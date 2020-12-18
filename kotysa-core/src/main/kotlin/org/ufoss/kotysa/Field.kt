/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KParameter
import kotlin.reflect.full.memberFunctions
import kotlin.reflect.full.primaryConstructor

public interface Field<T : Any> {
    public val properties: DefaultSqlClientCommon.Properties
    public val fieldNames: List<String>
    public val builder: (Row) -> T
}

public interface FieldNotNull<T : Any> : Field<T>

public interface FieldNullable<T : Any> : Field<T>

public class CountField<T : Any, U : Any> internal constructor(
        override val properties: DefaultSqlClientCommon.Properties,
        column: Column<T, U>?,
) : FieldNotNull<Int> {
    override val fieldNames: List<String> = listOf("COUNT(${column?.getFieldName(properties.tables.allColumns) ?: "*"})")

    override val builder: (Row) -> Int = { row ->
        row.getWithOffset(0, Int::class.javaObjectType)!!
    }
}

public sealed class ColumnField<T : Any, U : Any>(
        final override val properties: DefaultSqlClientCommon.Properties,
        column: Column<T, U>
) : Field<U> {
    internal val fieldName = column.getFieldName(properties.tables.allColumns)
    final override val fieldNames: List<String> = listOf(fieldName)

    @Suppress("UNCHECKED_CAST")
    final override val builder: (Row) -> U = { row ->
        row.getWithOffset(0, column.getKotysaColumn(properties.availableColumns).columnClass.javaObjectType as Class<U>)!!
    }
}

public class ColumnFieldNotNull<T : Any, U : Any> internal constructor(
        properties: DefaultSqlClientCommon.Properties,
        column: ColumnNotNull<T, U>
) : ColumnField<T, U>(properties, column), FieldNotNull<U>

public class ColumnFieldNullable<T : Any, U : Any> internal constructor(
        properties: DefaultSqlClientCommon.Properties,
        column: ColumnNullable<T, U>
) : ColumnField<T, U>(properties, column), FieldNullable<U>

/**
 * Selected field
 */
public class TableField<T : Any> internal constructor(
        override val properties: DefaultSqlClientCommon.Properties,
        internal val table: Table<T>,
) : Field<T> {

    override val fieldNames: List<String> =
            table.columns.map { column -> column.getFieldName(properties.tables.allColumns) }

    @Suppress("UNCHECKED_CAST")
    override val builder: (Row) -> T = { row ->
        val kotysaTable = table.getKotysaTable(properties.availableTables)
        val associatedColumns = mutableListOf<KotysaColumn<*, *>>()
        val constructor = getTableConstructor(kotysaTable.tableClass)
        val instance = with(constructor!!) {
            val args = mutableMapOf<KParameter, Any?>()
            parameters.forEach { param ->
                // get the mapped property with same name
                val column = kotysaTable.columns.firstOrNull { column ->
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
                    args[param] = row.getWithOffset(kotysaTable.columns.indexOf(column), column.columnClass.javaObjectType)
                } else {
                    require(param.isOptional) {
                        "Cannot instanciate Table \"${kotysaTable.tableClass.qualifiedName}\"," +
                                "parameter \"${param.name}\" is required and is not mapped to a Column"
                    }
                }
            }
            // invoke constructor
            callBy(args)
        }

        // Then try to invoke var or setter for each unassociated getter
        if (associatedColumns.size < table.columns.size) {
            kotysaTable.columns
                    .filter { column -> !associatedColumns.contains(column) }
                    .forEach { column ->
                        val getter = column.entityGetter
                        if (getter is KMutableProperty1<T, Any?>) {
                            getter.set(instance, row.getWithOffset(kotysaTable.columns.indexOf(column), column.columnClass.java))
                            associatedColumns.add(column)
                        } else {
                            val callable = getter.toCallable()
                            if (callable is KFunction<Any?>
                                    && (callable.name.startsWith("get")
                                            || callable.name.startsWith("is"))
                                    && callable.name.length > 3) {
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
                                    setter.call(instance, row.getWithOffset(kotysaTable.columns.indexOf(column), column.columnClass.java))
                                    associatedColumns.add(column)
                                }
                            }
                        }
                    }
        }
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

// Extension functions

internal fun <T : Any, U : Any> Column<T, U>.toField(properties: DefaultSqlClientCommon.Properties): ColumnField<T, U> {
    if (this is ColumnNotNull<T, U>) {
        return ColumnFieldNotNull(properties, this)
    }
    return ColumnFieldNullable(properties, this as ColumnNullable<T, U>)
}

internal fun Column<*, *>.getFieldName(availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>): String {
    val kotysaColumn = this.getKotysaColumn(availableColumns)
    val kotysaTable = kotysaColumn.table
    return if (kotysaTable is AliasedTable<*>) {
        "${kotysaTable.alias}."
    } else {
        "${kotysaColumn.table.name}."
    } + kotysaColumn.name
}

public fun <T : Any> Table<T>.toField(properties: DefaultSqlClientCommon.Properties): TableField<T> =
        TableField(properties, this)

/*
public interface Field {
    public val fieldName: String
}


public interface NotNullField : Field


public interface NullableField : Field

public class CountField<T : Any, U : Any> internal constructor(
        availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
        column: Column<T, U>?,
        internal val dbType: DbType
) : NotNullField {
    override val fieldName: String

    init {
        val counted = if (column != null) {
            column.getFieldName()
        } else {
            "*"
        }
        fieldName = "COUNT($counted)"
    }
}


@Suppress("UNCHECKED_CAST")
public abstract class ColumnField<T : Any, U : Any> internal constructor(
        availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
        column: Column<T, U>,
        internal val dbType: DbType
) : Field {

    internal val kotysaColumn: KotysaColumn<T, U>

    init {
        require(availableColumns.containsKey(column)) { "Requested column \"$column\" is not mapped" }
        kotysaColumn = availableColumns[column] as KotysaColumn<T, U>
    }

    override val fieldName: String = "${kotysaColumn.table.name}.${kotysaColumn.name}"
}


public class NotNullStringColumnField<T : Any> internal constructor(
        availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
        column: StringColumnNotNull<T>,
        dbType: DbType,
) : ColumnField<T, String>(availableColumns, column, dbType), NotNullField


public class NullableStringColumnField<T : Any> internal constructor(
        availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
        column: StringColumnNullable<T>,
        dbType: DbType,
) : ColumnField<T, String>(availableColumns, column, dbType), NullableField


public class NotNullLocalDateTimeColumnField<T : Any> internal constructor(
        availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
        column: LocalDateTimeColumnNotNull<T>,
        dbType: DbType,
) : ColumnField<T, LocalDateTime>(availableColumns, column, dbType), NotNullField


public class NullableLocalDateTimeColumnField<T : Any> internal constructor(
        availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
        column: LocalDateTimeColumnNullable<T>,
        dbType: DbType,
) : ColumnField<T, LocalDateTime>(availableColumns, column, dbType), NullableField

public class NotNullKotlinxLocalDateTimeColumnField<T : Any> internal constructor(
        availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
        column: KotlinxLocalDateTimeColumnNotNull<T>,
        dbType: DbType,
) : ColumnField<T, kotlinx.datetime.LocalDateTime>(availableColumns, column, dbType), NotNullField


public class NullableKotlinxLocalDateTimeColumnField<T : Any> internal constructor(
        availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
        column: KotlinxLocalDateTimeColumnNullable<T>,
        dbType: DbType,
) : ColumnField<T, kotlinx.datetime.LocalDateTime>(availableColumns, column, dbType), NullableField


public class NotNullLocalDateColumnField<T : Any> internal constructor(
        availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
        column: LocalDateColumnNotNull<T>,
        dbType: DbType,
) : ColumnField<T, LocalDate>(availableColumns, column, dbType), NotNullField


public class NullableLocalDateColumnField<T : Any> internal constructor(
        availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
        column: LocalDateColumnNullable<T>,
        dbType: DbType,
) : ColumnField<T, LocalDate>(availableColumns, column, dbType), NullableField

public class NotNullKotlinxLocalDateColumnField<T : Any> internal constructor(
        availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
        column: KotlinxLocalDateColumnNotNull<T>,
        dbType: DbType,
) : ColumnField<T, kotlinx.datetime.LocalDate>(availableColumns, column, dbType), NotNullField


public class NullableKotlinxLocalDateColumnField<T : Any> internal constructor(
        availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
        column: KotlinxLocalDateColumnNullable<T>,
        dbType: DbType,
) : ColumnField<T, kotlinx.datetime.LocalDate>(availableColumns, column, dbType), NullableField


public class NotNullOffsetDateTimeColumnField<T : Any> internal constructor(
        availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
        column: OffsetDateTimeColumnNotNull<T>,
        dbType: DbType,
) : ColumnField<T, OffsetDateTime>(availableColumns, column, dbType), NotNullField


public class NullableOffsetDateTimeColumnField<T : Any> internal constructor(
        availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
        column: OffsetDateTimeColumnNullable<T>,
        dbType: DbType,
) : ColumnField<T, OffsetDateTime>(availableColumns, column, dbType), NullableField


public class NotNullLocalTimeColumnField<T : Any> internal constructor(
        availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
        column: LocalTimeColumnNotNull<T>,
        dbType: DbType,
) : ColumnField<T, LocalTime>(availableColumns, column, dbType), NotNullField


public class NullableLocalTimeColumnField<T : Any> internal constructor(
        availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
        column: LocalTimeColumnNullable<T>,
        dbType: DbType,
) : ColumnField<T, LocalTime>(availableColumns, column, dbType), NullableField


public class NotNullBooleanColumnField<T : Any> internal constructor(
        availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
        column: BooleanColumnNotNull<T>,
        dbType: DbType,
) : ColumnField<T, Boolean>(availableColumns, column, dbType), NotNullField


public class NotNullUuidColumnField<T : Any> internal constructor(
        availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
        column: UuidColumnNotNull<T>,
        dbType: DbType,
) : ColumnField<T, UUID>(availableColumns, column, dbType), NotNullField


public class NullableUuidColumnField<T : Any> internal constructor(
        availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
        column: UuidColumnNullable<T>,
        dbType: DbType,
) : ColumnField<T, UUID>(availableColumns, column, dbType), NullableField


public class NotNullIntColumnField<T : Any> internal constructor(
        availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
        column: IntColumnNotNull<T>,
        dbType: DbType,
) : ColumnField<T, Int>(availableColumns, column, dbType), NotNullField


public class NullableIntColumnField<T : Any> internal constructor(
        availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
        column: IntColumnNullable<T>,
        dbType: DbType,
) : ColumnField<T, Int>(availableColumns, column, dbType), NullableField
*/
