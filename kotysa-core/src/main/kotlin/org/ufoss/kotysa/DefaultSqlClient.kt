/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import org.ufoss.kolog.Logger

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
                " DEFAULT ${column.defaultValue.defaultValue(tables.dbType)}"
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

    public fun <T : Any> insertSql(row: T, withReturn: Boolean = false): String {
        val insertSqlQuery = insertSqlQuery(row, withReturn)
        logger.debug { "Exec SQL (${tables.dbType.name}) : $insertSqlQuery" }
        return insertSqlQuery
    }

    // fixme 24/05/21 : does not work if set to private (fails in demo-kotlin project)
    public fun <T : Any> insertSqlQuery(row: T, withReturn: Boolean): String {
        val kotysaTable = tables.getTable(row::class)
        val columnNames = mutableSetOf<String>()
        val counter = Counter()
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
                variable(counter)
            }
        var prefix = ""
        var suffix = ""
        // on MSSQL identity cannot be set a value, must activate IDENTITY_INSERT
        if (tables.dbType == DbType.MSSQL
            && kotysaTable.columns.any { column -> column.isAutoIncrement && column.entityGetter(row) != null }
        ) {
            prefix = "SET IDENTITY_INSERT ${kotysaTable.name} ON\n"
            suffix = "\nSET IDENTITY_INSERT ${kotysaTable.name} OFF"
        }

        if (!withReturn || tables.dbType == DbType.MYSQL) {
            // If no return requested, or MySQL that does not provide native RETURNING style feature
            return "${prefix}INSERT INTO ${kotysaTable.name} (${columnNames.joinToString()}) VALUES ($values)$suffix"
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
            DbType.MSSQL -> "${prefix}INSERT INTO ${kotysaTable.name} (${columnNames.joinToString()}) OUTPUT $allTableColumnNames VALUES ($values)$suffix"
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

        val wheres = if (
            pkColumns.size == 1 &&
            pkColumns[0].isAutoIncrement &&
            pkColumns[0].entityGetter(row) == null
        ) {
            val selected = if (tables.dbType == DbType.MYSQL) {
                "(SELECT LAST_INSERT_ID())"
            } else {
                "?"
            }
            "${pkColumns[0].name} = $selected"
        } else {
            val counter = Counter()
            pkColumns
                .joinToString(" AND ") { column ->
                    "${column.name} = ${variable(counter)}"
                }
        }

        return "SELECT $allTableColumnNames FROM ${kotysaTable.name} WHERE $wheres"
    }

    public fun variable(counter: Counter): String =
        when {
            module == Module.SQLITE || module == Module.JDBC
                    || module == Module.R2DBC && tables.dbType == DbType.MYSQL -> "?"
            module == Module.R2DBC && (tables.dbType == DbType.H2 || tables.dbType == DbType.POSTGRESQL) -> "$${++counter.index}"
            module == Module.R2DBC && tables.dbType == DbType.MSSQL -> "@p${++counter.index}"
            else -> ":k${counter.index++}"
        }
}

public class Counter {
    internal var index = 0
}
