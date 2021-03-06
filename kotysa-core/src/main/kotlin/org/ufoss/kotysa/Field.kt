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

public interface Field<T> {
    public val properties: DefaultSqlClientCommon.Properties
    public val fieldNames: List<String>
    public val builder: (RowImpl) -> T
}

public interface FieldNotNull<T : Any> : Field<T>

public interface FieldNullable<T : Any> : Field<T?>

internal class CountField<T : Any, U : Any> internal constructor(
        override val properties: DefaultSqlClientCommon.Properties,
        column: Column<T, U>?,
) : FieldNotNull<Long> {
    override val fieldNames: List<String> = listOf("COUNT(${column?.getFieldName(properties.tables.allColumns) ?: "*"})")

    override val builder: (RowImpl) -> Long = { row ->
        row.getAndIncrement(Long::class.javaObjectType)!!
    }
}

internal class ColumnField<T : Any, U : Any>(
        override val properties: DefaultSqlClientCommon.Properties,
        column: Column<T, U>,
) : FieldNullable<U> {
    override val fieldNames: List<String> = listOf(column.getFieldName(properties.tables.allColumns))
    override val builder: (RowImpl) -> U? = { row -> row.getAndIncrement(column, properties) }
}

/**
 * Selected field
 */
internal class TableField<T : Any> internal constructor(
        override val properties: DefaultSqlClientCommon.Properties,
        internal val table: AbstractTable<T>,
) : Field<T> {

    override val fieldNames: List<String> =
            table.columns.map { column -> column.getFieldName(properties.tables.allColumns) }

    @Suppress("UNCHECKED_CAST")
    override val builder: (RowImpl) -> T = { row ->
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
                        "Cannot instantiate Table \"${kotysaTable.tableClass.qualifiedName}\"," +
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
                            getter.set(instance, row.getWithOffset(kotysaTable.columns.indexOf(column), column.columnClass.javaObjectType))
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
                                    setter.call(instance, row.getWithOffset(kotysaTable.columns.indexOf(column), column.columnClass.javaObjectType))
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

internal class FieldDsl<T : Any>(
        override val properties: DefaultSqlClientSelect.Properties<T>,
        private val dsl: (ValueProvider) -> T
) : FieldNotNull<T> {
    private val selectDsl = SelectDsl(properties)

    override val fieldNames: List<String> = FieldValueProvider(properties).initialize(dsl)

    override val builder: (RowImpl) -> T = { row ->
        selectDsl.row = row
        dsl(selectDsl)
    }
}

// Extension functions

internal fun <T : Any, U : Any> Column<T, U>.toField(
        properties: DefaultSqlClientCommon.Properties
): ColumnField<T, U> = ColumnField(properties, this)

internal fun <T : Any> AbstractTable<T>.toField(properties: DefaultSqlClientCommon.Properties): TableField<T> =
        TableField(properties, this)

internal fun Column<*, *>.getFieldName(availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>): String {
    val kotysaColumn = getKotysaColumn(availableColumns)
    val kotysaTable = kotysaColumn.table
    return "${kotysaTable.getFieldName()}.${kotysaColumn.name}"
}

internal fun Table<*>.getFieldName(availableTables: Map<Table<*>, KotysaTable<*>>) =
        getKotysaTable(availableTables).getFieldName()

private fun KotysaTable<*>.getFieldName() =
        if (this is AliasedTable<*>) {
            alias
        } else {
            name
        }
