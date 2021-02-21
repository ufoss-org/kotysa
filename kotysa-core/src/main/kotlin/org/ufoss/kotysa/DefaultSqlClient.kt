/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import org.ufoss.kolog.Logger
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.reflect.KClass


private fun tableMustBeMapped(tableName: String?) = "Requested table \"$tableName\" is not mapped"

@Suppress("UNCHECKED_CAST")
public fun <T : Any> Tables.getTable(table: Table<T>): KotysaTable<T> =
        requireNotNull(this.allTables[table]) { tableMustBeMapped(table.toString()) } as KotysaTable<T>

@Suppress("UNCHECKED_CAST")
public fun <T : Any> Tables.getTable(tableClass: KClass<out T>): KotysaTable<T> =
        requireNotNull(this.allTables.values.first { kotysaTable -> kotysaTable.tableClass == tableClass }) as KotysaTable<T>

@Suppress("UNCHECKED_CAST")
public fun <T : Any> Tables.getTable(column: Column<T, *>): KotysaTable<T> =
        requireNotNull(this.allColumns[column]) { "Requested column \"$column\" is not mapped" }.table as KotysaTable<T>

public fun <T : Any> Tables.checkTable(tableClass: KClass<out T>) {
    require(this.allTables.values.any { kotysaTable -> kotysaTable.tableClass == tableClass }) { tableMustBeMapped(tableClass.qualifiedName) }
}

private val logger = Logger.of<DefaultSqlClient>()


public interface DefaultSqlClient {
    public val tables: Tables

    public fun createTableSql(table: Table<*>): String {
        val kotysaTable = tables.getTable(table)

        val columns = kotysaTable.columns.joinToString { column ->
            if (tables.dbType == DbType.MYSQL && column.sqlType == SqlType.VARCHAR) {
                requireNotNull(column.size) { "Column ${column.name} : Varchar size is required in MySQL" }
            }
            val size = if (column.size != null) "(${column.size})" else ""
            val nullability = if (column.isNullable) "NULL" else "NOT NULL"
            val autoIncrement = if (column.isAutoIncrement && DbType.SQLITE != tables.dbType) {
                // SQLITE : The AUTOINCREMENT keyword imposes extra CPU, memory, disk space, and disk I/O overhead and should be avoided if not strictly needed.
                // It is usually not needed -> https://sqlite.org/autoinc.html
                // if this needs to be added later, sqlite syntax MUST be "column INTEGER PRIMARY KEY AUTOINCREMENT"
                " AUTO_INCREMENT"
            } else {
                ""
            }
            val default = if (column.defaultValue != null) {
                " DEFAULT ${column.defaultValue.defaultValue()}"
            } else {
                ""
            }
            "${column.name} ${column.sqlType.fullType}$size $nullability$autoIncrement$default"
        }

        val pk = kotysaTable.primaryKey
        var primaryKey = if (pk.name != null) {
            "CONSTRAINT ${pk.name} "
        } else {
            ""
        }
        primaryKey += "PRIMARY KEY (${pk.columns.joinToString { it.name }})"

        val foreignKeys =
                if (kotysaTable.foreignKeys.isEmpty()) {
                    ""
                } else {
                    kotysaTable.foreignKeys.joinToString(prefix = ", ") { foreignKey ->
                        var foreignKeyStatement = if (foreignKey.name != null) {
                            "CONSTRAINT ${foreignKey.name} "
                        } else {
                            ""
                        }
                        val referencedTable = tables.allColumns[foreignKey.references.values.first()]?.table
                                ?: error("Referenced table of column ${foreignKey.references.values.first()} is not mapped")
                        foreignKeyStatement += "FOREIGN KEY (${foreignKey.references.keys.joinToString { it.name }})" +
                                " REFERENCES ${referencedTable.name}" +
                                " (${foreignKey.references.values.joinToString { it.name }})"
                        foreignKeyStatement
                    }
                }

        val createTableSql = "CREATE TABLE IF NOT EXISTS ${kotysaTable.name} ($columns, $primaryKey$foreignKeys)"
        logger.debug { "Exec SQL (${tables.dbType.name}) : $createTableSql" }
        return createTableSql
    }

    public fun <T : Any> insertSql(row: T): String {
        val insertSqlQuery = insertSqlQuery(row)
        logger.debug { "Exec SQL (${tables.dbType.name}) : $insertSqlQuery" }
        return insertSqlQuery
    }

    public fun <T : Any> insertSqlDebug(row: T) {
        logger.debug { "Exec SQL (${tables.dbType.name}) : ${insertSqlQuery(row)}" }
    }

    public fun <T : Any> insertSqlQuery(row: T): String {
        val kotysaTable = tables.getTable(row::class)
        val columnNames = mutableSetOf<String>()
        var index = 0
        val values = kotysaTable.columns
                // filter out null values with default value or Serial type
                .filterNot { column ->
                    column.entityGetter(row) == null
                            && (column.defaultValue != null || SqlType.SERIAL == column.sqlType)
                }
                .joinToString { column ->
                    columnNames.add(column.name)
                    if (DbType.SQLITE == tables.dbType) {
                        "?"
                    } else {
                        ":k${index++}"
                    }
                }

        return "INSERT INTO ${kotysaTable.name} (${columnNames.joinToString()}) VALUES ($values)"
    }
}

internal fun Any?.dbValue(): String = when (this) {
    null -> "null"
    is String -> "$this"
    is Boolean -> "$this"
    is UUID -> "$this"
    is Int -> "$this"
    is LocalDate -> this.format(DateTimeFormatter.ISO_LOCAL_DATE)
    is LocalDateTime -> this.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    /*DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .append(DateTimeFormatter.ISO_LOCAL_DATE)
            .appendLiteral(' ')
            .append(DateTimeFormatter.ISO_LOCAL_TIME)
            .optionalStart()
            .appendFraction(MICRO_OF_SECOND, 0, 6, true)
            .optionalEnd()
            .toFormatter(Locale.ENGLISH))*/
    is LocalTime -> /*"+" + */this.format(DateTimeFormatter.ISO_LOCAL_TIME)
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

private fun Any?.defaultValue(): String = when (this) {
    is Int -> "$this"
    else -> "'${this.dbValue()}'"
}
