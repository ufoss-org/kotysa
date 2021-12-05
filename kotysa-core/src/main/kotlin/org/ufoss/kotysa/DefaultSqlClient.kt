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


private val logger = Logger.of<DefaultSqlClient>()

public interface DefaultSqlClient {
    public val tables: Tables
    public val module: Module

    public fun createTableSql(table: Table<*>, ifNotExists: Boolean): String {
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
                if (DbType.MSSQL == tables.dbType) {
                    " IDENTITY"
                } else {
                    " AUTO_INCREMENT"
                }
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

        var suffix = ""
        val prefix = if (ifNotExists) {
            if (DbType.MSSQL == tables.dbType) {
                suffix = """
                END"""

                """IF NOT EXISTS(SELECT name FROM sys.sysobjects WHERE Name = N'${kotysaTable.name}' AND xtype = N'U')
                BEGIN
                CREATE TABLE"""
            } else {
                "CREATE TABLE IF NOT EXISTS"
            }
        } else {
            "CREATE TABLE"
        }

        val createTableSql = "$prefix ${kotysaTable.name} ($columns, $primaryKey$foreignKeys)$suffix"
        logger.debug { "Exec SQL (${tables.dbType.name}) : $createTableSql" }
        return createTableSql
    }

    public fun <T : Any> insertSql(row: T): String {
        val insertSqlQuery = insertSqlQuery(row)
        logger.debug { "Exec SQL (${tables.dbType.name}) : $insertSqlQuery" }
        return insertSqlQuery
    }

    // fixme 24/05/21 : does not work if set to private (fails in demo-kotlin project)
    public fun <T : Any> insertSqlQuery(row: T): String {
        val kotysaTable = tables.getTable(row::class)
        val columnNames = mutableSetOf<String>()
        var index = 0
        val values = kotysaTable.columns
            // filter out null values with default value or Serial types
            .filterNot { column ->
                column.entityGetter(row) == null
                        && (column.defaultValue != null
                        || column.isAutoIncrement
                        || SqlType.SERIAL == column.sqlType
                        || SqlType.BIGSERIAL == column.sqlType)
            }
            .joinToString { column ->
                columnNames.add(column.name)
                when (module) {
                    Module.SQLITE, Module.JDBC -> "?"
                    else -> ":k${index++}"
                }
            }

        val allTableColumnNames = kotysaTable.columns
            .joinToString { column ->
                if (tables.dbType == DbType.MSSQL) {
                    "INSERTED." + column.name
                } else {
                    column.name
                }
            }

        return when (tables.dbType) {
            DbType.H2 -> "SELECT $allTableColumnNames FROM FINAL TABLE (INSERT INTO ${kotysaTable.name} (${columnNames.joinToString()}) VALUES ($values))"
            DbType.MSSQL -> "INSERT INTO ${kotysaTable.name} (${columnNames.joinToString()}) OUTPUT $allTableColumnNames VALUES ($values)"
            DbType.MYSQL -> "INSERT INTO ${kotysaTable.name} (${columnNames.joinToString()}) VALUES ($values)"
            else -> "INSERT INTO ${kotysaTable.name} (${columnNames.joinToString()}) VALUES ($values) RETURNING $allTableColumnNames"
        }
    }

    public fun <T : Any> lastInsertedSql(row: T): String {
        val lastInsertedQuery = lastInsertedQuery(row)
        logger.debug { "Exec SQL (${tables.dbType.name}) : $lastInsertedQuery" }
        return lastInsertedQuery
    }

    public fun <T : Any> lastInsertedQuery(row: T): String {
        val kotysaTable = tables.getTable(row::class)
        val pkColumns = kotysaTable.primaryKey.columns

        val allTableColumnNames = kotysaTable.columns
            .joinToString { column -> column.name }

        val wheres =
            if (pkColumns.size == 1 && pkColumns[0].isAutoIncrement) {
                "${pkColumns[0].name} = (SELECT LAST_INSERT_ID())"
            } else {
                var index = 0
                pkColumns
                    .joinToString(" AND ") { column ->
                        when (module) {
                            Module.SQLITE, Module.JDBC -> "${column.name} = ?"
                            else -> "${column.name} = :k${index++}"
                        }
                    }
            }

        return "SELECT $allTableColumnNames FROM ${kotysaTable.name} WHERE $wheres"
    }
}

internal fun Any?.dbValue(): String = when (this) {
    null -> "null"
    is String -> "$this"
    is Boolean -> "$this"
    is UUID -> "$this"
    is Int -> "$this"
    is Long -> "$this"
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
    is Long -> "$this"
    else -> "'${this.dbValue()}'"
}
