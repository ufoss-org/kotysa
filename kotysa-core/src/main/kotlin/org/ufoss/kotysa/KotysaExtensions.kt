/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
public fun <T : Any> Tables.getTable(table: Table<T>): KotysaTable<T> =
    requireNotNull(this.allTables[table]) { "Requested table \"$table\" is not mapped" } as KotysaTable<T>

@Suppress("UNCHECKED_CAST")
public fun <T : Any> Tables.getTable(tableClass: KClass<out T>): KotysaTable<T> =
    requireNotNull(this.allTables.values.first { kotysaTable -> kotysaTable.tableClass == tableClass }) as KotysaTable<T>

public fun List<WhereClauseWithType>.dbValues(tables: Tables): List<Any> =
    mapNotNull { typedWhereClause ->
        if (typedWhereClause.whereClause is WhereClauseValue) {
            typedWhereClause.whereClause.value
        } else {
            null
        }
    }.map { value ->
        if (value is Set<*>) {
            // create new Set with transformed values
            mutableSetOf<Any?>().apply {
                value.forEach { dbVal ->
                    add(tables.getDbValue(dbVal))
                }
            }
        } else {
            tables.getDbValue(value)
        } as Any
    }

@Suppress("UNCHECKED_CAST")
public operator fun <T : Any, U : Any, V : Column<T, U>> V.get(alias: String): V =
    (this as DbColumn<T, U>).clone().apply { (this as DbColumn<T, U>).alias = alias } as V

@Suppress("UNCHECKED_CAST")
internal fun <T : Any> Table<T>.getKotysaTable(availableTables: Map<Table<*>, KotysaTable<*>>): KotysaTable<T> {
    return requireNotNull(availableTables[this]) { "Requested table \"$this\" is not mapped" } as KotysaTable<T>
}

@Suppress("UNCHECKED_CAST")
internal fun <T : Any, U: Any> Column<T, U>.getKotysaColumn(availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>): KotysaColumn<T, U> {
    return requireNotNull(availableColumns[this]) { "Requested column \"$this\" is not mapped" } as KotysaColumn<T, U>
}

internal fun <T : Any, U : Any> Column<T, U>.toField(
    properties: DefaultSqlClientCommon.Properties,
    classifier: FieldClassifier,
): ColumnField<T, U> = ColumnField(properties, this, classifier)

public fun <T : Any> AbstractTable<T>.toField(
    availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
    availableTables: Map<Table<*>, KotysaTable<*>>,
): Field<T> =
    TableField(availableColumns, availableTables, this)

internal fun Field<*>.getFieldName(): String {
    var fieldName = fieldNames.joinToString()
    if (alias != null) {
        fieldName += " AS \"$alias\""
    }
    return fieldName
}

internal fun Column<*, *>.getFieldName(availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>): String {
    if ((this as DbColumn<*, *>).alias != null) {
        return "\"${this.alias!!}\""
    }
    val kotysaColumn = getKotysaColumn(availableColumns)
    val kotysaTable = kotysaColumn.table
    return "${kotysaTable.getFieldName()}.${kotysaColumn.name}"
}

internal fun Table<*>.getFieldName(availableTables: Map<Table<*>, KotysaTable<*>>) =
    getKotysaTable(availableTables).getFieldName()

private fun KotysaTable<*>.getFieldName() = name
/*if (this is AliasedTable<*>) {
    alias
} else {
    name
}*/

@Suppress("UNCHECKED_CAST")
internal fun <T : Any> DefaultSqlClientCommon.Properties.executeSubQuery(
    dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>,
): SubQueryResult<T> {
    val subQuery = SqlClientSubQueryImpl.Scope(this)
    // invoke sub-query
    val result = dsl(subQuery)
    // add all sub-query parameters, if any, to parent's properties
    if (subQuery.properties.parameters.isNotEmpty()) {
        this.parameters.addAll(subQuery.properties.parameters)
    }
    return SubQueryResult(subQuery.properties as DefaultSqlClientSelect.Properties<T>, result)
}
