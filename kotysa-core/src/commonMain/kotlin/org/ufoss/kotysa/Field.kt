/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import org.ufoss.kotysa.columns.TsvectorColumn
import org.ufoss.kotysa.postgresql.Tsquery
import java.math.BigDecimal
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KParameter
import kotlin.reflect.full.memberFunctions
import kotlin.reflect.full.primaryConstructor

public sealed interface Field<T> {
    public val fieldNames: List<String>
    public var alias: String?
    public val builder: (RowImpl) -> T
}

internal sealed class AbstractField<T> : Field<T> {
    final override var alias: String? = null
}

public enum class FieldClassifier {
    NONE, DISTINCT, MAX, MIN
}

public interface FieldNotNull<T : Any> : Field<T>

public interface FieldNullable<T : Any> : Field<T?>

internal class CountField<T : Any, U : Any> internal constructor(
    properties: DefaultSqlClientCommon.Properties,
    column: Column<T, U>?,
) : AbstractField<Long>(), FieldNotNull<Long> {
    override val fieldNames: List<String> =
        listOf("COUNT(${column?.getFieldName(properties.tables.allColumns, properties.tables.dbType) ?: "*"})")

    override val builder: (RowImpl) -> Long = { row -> row.getAndIncrement(Long::class.javaObjectType)!! }
}

internal class ColumnField<T : Any, U : Any> internal constructor(
    properties: DefaultSqlClientCommon.Properties,
    column: Column<T, U>,
    classifier: FieldClassifier,
) : AbstractField<U?>(), FieldNullable<U> {
    override val fieldNames: List<String> = when (classifier) {
        FieldClassifier.NONE -> listOf(column.getFieldName(properties.tables.allColumns, properties.tables.dbType))
        FieldClassifier.DISTINCT -> listOf(
            "DISTINCT ${
                column.getFieldName(
                    properties.tables.allColumns,
                    properties.tables.dbType
                )
            }"
        )

        FieldClassifier.MAX -> listOf(
            "MAX(${
                column.getFieldName(
                    properties.tables.allColumns,
                    properties.tables.dbType
                )
            })"
        )

        FieldClassifier.MIN -> listOf(
            "MIN(${
                column.getFieldName(
                    properties.tables.allColumns,
                    properties.tables.dbType
                )
            })"
        )
    }
    override val builder: (RowImpl) -> U? = { row -> row.getAndIncrement(column, properties) }
}

internal class AvgField<T : Any, U : Any> internal constructor(
    properties: DefaultSqlClientCommon.Properties,
    column: Column<T, U>,
) : AbstractField<BigDecimal>(), FieldNotNull<BigDecimal> {
    override val fieldNames: List<String> = listOf(
        "AVG(${
            column.getFieldName(
                properties.tables.allColumns,
                properties.tables.dbType
            )
        })"
    )

    override val builder: (RowImpl) -> BigDecimal = { row ->
        when {
            properties.tables.dbType == DbType.H2
                    && (properties.module == Module.R2DBC || properties.module == Module.SPRING_R2DBC) ->
                row.getAndIncrement(Double::class.javaObjectType)!!.toBigDecimal()

            else -> row.getAndIncrement(BigDecimal::class.javaObjectType)!!
        }
    }
}

internal class LongSumField<T : Any, U : Any> internal constructor(
    properties: DefaultSqlClientCommon.Properties,
    column: Column<T, U>,
) : AbstractField<Long>(), FieldNotNull<Long> {
    override val fieldNames: List<String> = listOf(
        "SUM(${
            column.getFieldName(
                properties.tables.allColumns,
                properties.tables.dbType
            )
        })"
    )

    override val builder: (RowImpl) -> Long = { row ->
        when {
            properties.tables.dbType == DbType.MYSQL && properties.dbAccessType == DbAccessType.R2DBC ->
                row.getAndIncrement(BigDecimal::class.javaObjectType)!!.toLong()

            properties.tables.dbType == DbType.H2 && properties.dbAccessType == DbAccessType.R2DBC ->
                if (column.getKotysaColumn(properties.tables.allColumns).columnClass == Long::class) {
                    row.getAndIncrement(BigDecimal::class.javaObjectType)!!.toLong()
                } else {
                    row.getAndIncrement(Long::class.javaObjectType)!!
                }

            else -> row.getAndIncrement(Long::class.javaObjectType)!!
        }
    }
}

internal class TableField<T : Any> internal constructor(
    availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
    availableTables: Map<Table<*>, KotysaTable<*>>,
    internal val table: AbstractTable<T>,
    dbType: DbType,
) : AbstractField<T>() {

    override val fieldNames: List<String> =
        table.kotysaColumns
            // tsvector should not be fetched when querying a table
            .filter { column -> column.sqlType != SqlType.TSVECTOR }
            .map { column -> column.getFieldName(availableColumns, dbType) }

    override val builder: (RowImpl) -> T = { row ->
        val kotysaTable = table.getKotysaTable(availableTables)
        val associatedColumns = mutableListOf<KotysaColumn<*, *>>()
        val constructor = getTableConstructor(kotysaTable.tableClass)
        val instance = with(constructor!!) {
            val args = mutableMapOf<KParameter, Any?>()
            parameters.forEach { param ->
                // get the mapped property with same name
                val column = kotysaTable.dbColumns
                    .firstOrNull { column ->
                        var getterMatch = false
                        val getterName = column.entityGetter.toCallable().name
                        if (getterName.startsWith("get") && getterName.length > 3) {
                            if (getterName.substring(3).equals(param.name!!, ignoreCase = true)) {
                                getterMatch = true
                            }
                        }
                        val matchFound = getterMatch || (getterName == param.name)
                        if (matchFound) {
                            associatedColumns.add(column)
                        }
                        matchFound
                    }
                if (column != null) {
                    args[param] =
                        row.getWithOffset(kotysaTable.columns.indexOf(column), column.columnClass.javaObjectType)
                } else {
                    require(param.isOptional) {
                        "Cannot instantiate Table \"${kotysaTable.tableClass.qualifiedName}\"," +
                                "parameter \"${param.name}\" is required and is not mapped to a Column"
                    }
                }
            }
            // invoke constructor
            callBy(args)
        }

        // Then try to invoke var or setter for each unassociated getter
        if (associatedColumns.size < table.kotysaColumns.size) {
            kotysaTable.dbColumns
                .filter { column -> !associatedColumns.contains(column) }
                .forEach { column ->
                    val getter = column.entityGetter
                    if (getter is KMutableProperty1<T, Any?>) {
                        getter.set(
                            instance,
                            row.getWithOffset(kotysaTable.columns.indexOf(column), column.columnClass.javaObjectType)
                        )
                        associatedColumns.add(column)
                    } else {
                        val callable = getter.toCallable()
                        if (callable is KFunction<Any?>
                            && (callable.name.startsWith("get")
                                    || callable.name.startsWith("is"))
                            && callable.name.length > 3
                        ) {
                            // try to find setter
                            val setter = if (callable.name.startsWith("get")) {
                                kotysaTable.tableClass.memberFunctions.firstOrNull { function ->
                                    function.name == callable.name.replaceFirst("g", "s")
                                            && function.parameters.size == 2
                                }
                            } else {
                                // then "is" for Boolean
                                kotysaTable.tableClass.memberFunctions.firstOrNull { function ->
                                    function.name == callable.name.replaceFirst("is", "set")
                                            && function.parameters.size == 2
                                }
                            }
                            if (setter != null) {
                                setter.call(
                                    instance,
                                    row.getWithOffset(
                                        kotysaTable.columns.indexOf(column),
                                        column.columnClass.javaObjectType
                                    )
                                )
                                associatedColumns.add(column)
                            }
                        }
                    }
                }
        }
        // increment row index by the number of selected columns in this table
        row.incrementWithDelayedIndex()
        instance
    }

    private fun getTableConstructor(tableClass: KClass<T>) = with(tableClass) {
        if (primaryConstructor != null) {
            primaryConstructor
        } else {
            var nbParameters = -1
            var mostCompleteConstructor: KFunction<T>? = null
            constructors.forEach { constructor ->
                if (constructor.parameters.size > nbParameters) {
                    nbParameters = constructor.parameters.size
                    mostCompleteConstructor = constructor
                }
            }
            mostCompleteConstructor
        }
    }
}

internal class SubQueryField<T : Any> internal constructor(
    private val subQueryReturn: SqlClientSubQuery.Return<T>,
    override val builder: (RowImpl) -> T?,
    private val parentProperties: DefaultSqlClientCommon.Properties,
) : AbstractField<T?>(), FieldNullable<T> {
    override val fieldNames get() = listOf("( ${subQueryReturn.sql(parentProperties)} )")
}

internal class CaseWhenExistsSubQueryField<T : Any, U : Any> internal constructor(
    private val dbType: DbType,
    private val subQueryReturn: SqlClientSubQuery.Return<T>,
    private val then: U,
    private val elseVal: U,
    private val parentProperties: DefaultSqlClientCommon.Properties,
) : AbstractField<U>(), FieldNotNull<U> {
    override val fieldNames
        get() = listOf(
            "CASE WHEN\nEXISTS( ${subQueryReturn.sql(parentProperties)} )\n" +
                    "THEN ${then.defaultValue(dbType)} ELSE ${elseVal.defaultValue(dbType)}\nEND"
        )

    override val builder: (RowImpl) -> U = { row -> row.getAndIncrement(then::class.javaObjectType)!! }
}

internal class StarField<T : Any> internal constructor(
    override val builder: (RowImpl) -> T?,
) : AbstractField<T?>(), FieldNullable<T> {
    override val fieldNames: List<String> = listOf("*")
}

internal class FieldDsl<T : Any>(
    properties: DefaultSqlClientSelect.Properties<T>,
    private val dsl: (ValueProvider) -> T
) : AbstractField<T>(), FieldNotNull<T> {
    private val selectDsl = SelectDsl(properties)

    override val fieldNames: List<String> = FieldValueProvider(properties).initialize(dsl)

    override val builder: (RowImpl) -> T = { row ->
        selectDsl.row = row
        dsl(selectDsl)
    }
}

internal class TsRankCdField internal constructor(
    properties: DefaultSqlClientCommon.Properties,
    tsvectorColumn: TsvectorColumn<*>,
    tsquery: Tsquery,
) : AbstractField<Float>(), FieldNotNull<Float> {
    override val fieldNames: List<String> =
        listOf(
            "ts_rank_cd(${tsvectorColumn.getFieldName(properties.tables.allColumns, properties.tables.dbType)}," +
                    "${tsquery.alias})"
        )

    override val builder: (RowImpl) -> Float = { row -> row.getAndIncrement(Float::class.javaObjectType)!! }
}
