/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*
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
public operator fun <T : Any, U : Table<T>> U.get(alias: String): U {
    // set tableAlias on all columns
    (this as AbstractTable<T>).kotysaColumns.forEach { column -> column.tableAlias = alias }
    return this
}

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
    dbType: DbType,
): Field<T> =
    TableField(availableColumns, availableTables, this, dbType)

internal fun Field<*>.getFieldName(dbType: DbType): String {
    var fieldName = fieldNames.joinToString()
    if (alias != null) {
        val aliasPart = when(dbType) {
            DbType.MSSQL -> " AS '$alias'"
            else -> " AS `$alias`"
        }
        fieldName += aliasPart
    }
    return fieldName
}

internal fun Column<*, *>.getFieldName(
    availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>,
    dbType: DbType,
): String {
    if ((this as DbColumn<*, *>).alias != null) {
        return when (dbType) {
            DbType.MSSQL -> alias!!
            else -> "`${alias!!}`"
        }
    }
    val kotysaColumn = getKotysaColumn(availableColumns)
    val tablePart = if (tableAlias != null) {
        val tabAlias = tableAlias!!
        // remove tableAlias on all columns
        (kotysaColumn.table.table as AbstractTable<*>).kotysaColumns.forEach { column -> column.tableAlias = null }
        tabAlias
    } else {
        kotysaColumn.table.name
    }
    return "$tablePart.${kotysaColumn.name}"
}

internal fun Table<*>.getFieldName(availableTables: Map<Table<*>, KotysaTable<*>>) =
    getKotysaTable(availableTables).name

@Suppress("UNCHECKED_CAST")
internal fun <T : Any> DefaultSqlClientCommon.Properties.executeSubQuery(
    dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>,
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

internal fun Any?.dbValue(dbType: DbType): String = when (this) {
    null -> "null"
    is String -> "$this"
    is Boolean -> when (dbType) {
        DbType.SQLITE -> if (this) "1" else "0"
        DbType.MSSQL -> "'$this'"
        else -> "$this"
    }
    is UUID -> "$this"
    is Int -> "$this"
    is Long -> "$this"
    is LocalDate -> this.format(DateTimeFormatter.ISO_LOCAL_DATE)
    is LocalDateTime -> this.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    is LocalTime -> this.format(DateTimeFormatter.ISO_LOCAL_TIME)
    is OffsetDateTime -> this.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
    else -> when (this::class.qualifiedName) {
        "kotlinx.datetime.LocalDate" -> this.toString()
        "kotlinx.datetime.LocalDateTime" -> {
            val kotlinxLocalDateTime = this as kotlinx.datetime.LocalDateTime
            if (kotlinxLocalDateTime.second == 0 && kotlinxLocalDateTime.nanosecond == 0) {
                "$kotlinxLocalDateTime:00" // missing seconds
            } else {
                kotlinxLocalDateTime.toString()
            }
        }
        else -> throw RuntimeException("${this.javaClass.canonicalName} is not supported yet")
    }
}

internal fun Any?.defaultValue(dbType: DbType): String = when (this) {
    is Boolean -> if (DbType.SQLITE == dbType) {
        if (this) "'1'" else "'0'"
    } else {
        this.dbValue(dbType)
    }
    is Int -> "$this"
    is Long -> "$this"
    else -> "'${this.dbValue(dbType)}'"
}

internal fun DefaultSqlClientCommon.Properties.variable() = when {
    module == Module.SQLITE || module == Module.JDBC
            || module == Module.R2DBC && tables.dbType == DbType.MYSQL -> "?"
    module == Module.R2DBC && (tables.dbType == DbType.H2 || tables.dbType == DbType.POSTGRESQL) -> "$${++this.index}"
    module == Module.R2DBC && tables.dbType == DbType.MSSQL -> "@p${++index}"
    else -> ":k${this.index++}"
}
