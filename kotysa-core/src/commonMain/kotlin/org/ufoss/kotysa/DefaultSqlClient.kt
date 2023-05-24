/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import org.ufoss.kolog.Logger

private val logger = Logger.of<DefaultSqlClient>()

public interface DefaultSqlClient {
    public val tables: Tables
    public val module: Module

    public fun createTableSql(table: Table<*>, ifNotExists: Boolean): CreateTableResult {
        val kotysaTable = tables.getTable(table)

        val columns = createTableColumns(kotysaTable)

        val pk = kotysaTable.primaryKey
        var primaryKey = if (pk.name != null) {
            "CONSTRAINT ${pk.name} "
        } else {
            ""
        }
        primaryKey += "PRIMARY KEY (${pk.columns.joinToString { it.name }})"

        val foreignKeys = createTableForeignKeys(kotysaTable)

        var createTableContent = "${kotysaTable.name} ($columns, $primaryKey$foreignKeys)"

        var suffix = ""
        val prefix = if (ifNotExists) {
            when (tables.dbType) {
                DbType.MSSQL -> {
                    suffix = "\nEND"

                    """
                    IF NOT EXISTS(SELECT name FROM sys.sysobjects WHERE Name = N'${kotysaTable.name}' AND xtype = N'U')
                    BEGIN
                      CREATE TABLE """
                }

                DbType.ORACLE -> {
                    suffix = """';
                      EXCEPTION
                        WHEN OTHERS THEN
                          IF SQLCODE = -955 THEN
                            NULL; -- suppresses ORA-00955 exception
                          ELSE
                             RAISE;
                          END IF;
                      END;"""

                    // Escape quote by another quote
                    createTableContent = createTableContent.replace("'", "''")

                    """
                    declare
                    begin
                      execute immediate 'CREATE TABLE """
                }

                else -> "CREATE TABLE IF NOT EXISTS "
            }
        } else {
            "CREATE TABLE "
        }

        val createTableSql = "$prefix$createTableContent$suffix"
        logger.debug { "Exec SQL (${tables.dbType.name}) : $createTableSql" }

        // create indexes
        val createIndexes = kotysaTable.kotysaIndexes.map { index ->
            val indexTypeLabel = index.type?.name ?: ""
            var indexName = index.name
                ?:
                // build custom index name
                index.columns
                    .joinToString("_", "${kotysaTable.name}_") { column ->
                        column.name
                    }
            if (indexTypeLabel.isNotEmpty()) {
                indexName += "_$indexTypeLabel"
            }
            val indexColumns = index.columns.joinToString { column -> column.name }
            val createIndexSql = when (index.type) {
                IndexType.GIN, IndexType.GIST ->
                    "CREATE INDEX $indexName ON ${kotysaTable.name} USING $indexTypeLabel ($indexColumns)"

                else -> "CREATE $indexTypeLabel INDEX $indexName ON ${kotysaTable.name} ($indexColumns)"
            }
            logger.debug { "Exec SQL (${tables.dbType.name}) : $createIndexSql" }
            CreateIndexResult(indexName, createIndexSql)
        }

        return CreateTableResult(createTableSql, createIndexes)
    }

    public fun createTableForeignKeys(kotysaTable: KotysaTable<out Any>): String =
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

    public fun createTableColumns(kotysaTable: KotysaTable<out Any>): String {
        val createDbColumns = kotysaTable.dbColumns
            .joinToString { column ->
                if (tables.dbType == DbType.MYSQL && column.sqlType == SqlType.VARCHAR) {
                    requireNotNull(column.size) { "Column ${column.name} : Varchar size is required in MySQL" }
                }
                var parameters = ""
                if (column.size != null) {
                    parameters = "(${column.size}"
                    if (column.decimals != null) {
                        parameters += ",${column.decimals}"
                    }
                    parameters += ")"
                }
                var nullability = if (column.isNullable) " NULL" else " NOT NULL"
                val autoIncrementOrIdentity = when {
                    column.identity != null ->
                        when (tables.dbType) {
                            DbType.ORACLE -> {
                                nullability = "" // Oracle syntax impose to remove nullability
                                " GENERATED BY DEFAULT ON NULL AS IDENTITY"
                            }
                            DbType.MSSQL -> " IDENTITY"
                            else -> " GENERATED BY DEFAULT AS IDENTITY"
                        }

                    column.isAutoIncrement && DbType.SQLITE != tables.dbType ->
                        // SQLITE : The AUTOINCREMENT keyword imposes extra CPU, memory, disk space, and disk I/O
                        // overhead and should be avoided if not strictly needed.
                        // It is usually not needed -> https://sqlite.org/autoinc.html
                        // if this needs to be added later, sqlite syntax MUST be
                        // "column INTEGER PRIMARY KEY AUTOINCREMENT"
                        " AUTO_INCREMENT"

                    else -> ""
                }
                val default = if (column.defaultValue != null) {
                    " DEFAULT ${column.defaultValue.defaultValue(tables.dbType)}"
                } else {
                    ""
                }
                "${column.name} ${column.sqlType.fullType}$parameters$autoIncrementOrIdentity$default$nullability"
            }

        val otherColumns = kotysaTable.columns
            .filterIsInstance<KotysaColumnTsvector<*, *>>()
        val createOtherColumns = if (otherColumns.isEmpty()) {
            ""
        } else {
            otherColumns
                .joinToString(prefix = ", ") { column ->
                    val columns = column.kotysaColumns
                        .joinToString(" || ' ' || ") { col ->
                            if (col.isNullable) {
                                // Use coalesce to ensure that field will be indexed
                                "coalesce(${col.name}, '')"
                            } else {
                                col.name
                            }
                        }
                    // see https://www.postgresql.org/docs/current/textsearch-tables.html#TEXTSEARCH-TABLES-INDEX
                    "${column.name} ${column.sqlType.fullType} GENERATED ALWAYS " +
                            "AS(to_tsvector('${column.tsvectorType}', $columns)) STORED"
                }
        }

        return "$createDbColumns$createOtherColumns"
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
        var prefix = ""
        var suffix = ""
        // on MSSQL identity cannot be set a value, must activate IDENTITY_INSERT
        if (tables.dbType == DbType.MSSQL
            && kotysaTable.columns
                .filterIsInstance<KotysaColumnDb<T, *>>()
                .any { column -> column.isAutoIncrement && column.entityGetter(row) != null }
        ) {
            prefix = "SET IDENTITY_INSERT ${kotysaTable.name} ON\n"
            suffix = "\nSET IDENTITY_INSERT ${kotysaTable.name} OFF"
        }

        if (
            !withReturn
            || tables.dbType == DbType.MYSQL
            // no RETURNING for R2DBC, it will use statement.returnGeneratedValues
            || (tables.dbType == DbType.ORACLE && (module == Module.R2DBC
                    || module == Module.SPRING_R2DBC
                    || module == Module.VERTX_SQL_CLIENT))
        ) {
            val values = insertValues(row, kotysaTable, counter, columnNames, false)
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

        val forceQuestionMark = tables.dbType == DbType.ORACLE
        val values = insertValues(row, kotysaTable, counter, columnNames, forceQuestionMark)

        return when (tables.dbType) {
            DbType.H2 -> "SELECT $allTableColumnNames FROM FINAL TABLE (INSERT INTO ${kotysaTable.name} " +
                    "(${columnNames.joinToString()}) VALUES ($values))"

            DbType.MSSQL -> "${prefix}INSERT INTO ${kotysaTable.name} (${columnNames.joinToString()}) OUTPUT " +
                    "$allTableColumnNames VALUES ($values)$suffix"

            DbType.ORACLE -> {
                // must add INTO clause with RETURNING
                val returningInto = kotysaTable.columns
                    .joinToString(prefix = " INTO ") { "?" }
                "INSERT INTO ${kotysaTable.name} (${columnNames.joinToString()}) VALUES ($values) RETURNING " +
                        "$allTableColumnNames$returningInto"
            }

            else -> "INSERT INTO ${kotysaTable.name} (${columnNames.joinToString()}) VALUES ($values) RETURNING " +
                    allTableColumnNames
        }
    }

    public fun <T : Any> insertValues(
        row: T,
        kotysaTable: KotysaTable<T>,
        counter: Counter,
        columnNames: MutableSet<String>,
        forceQuestionMark: Boolean
    ): String =
        kotysaTable.dbColumns
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
                if (forceQuestionMark) {
                    "?"
                } else {
                    variable(counter)
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

        val pkFirstColumn = pkColumns.elementAt(0)
        val wheres = if (
            pkColumns.size == 1 &&
            pkFirstColumn.isAutoIncrement &&
            pkFirstColumn.entityGetter(row) == null
        ) {
            val selected = if (tables.dbType == DbType.MYSQL) {
                "(SELECT LAST_INSERT_ID())"
            } else {
                "?"
            }
            "${pkFirstColumn.name} = $selected"
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
                    || (module == Module.R2DBC && tables.dbType == DbType.MYSQL)
                    || (module == Module.VERTX_SQL_CLIENT && (tables.dbType == DbType.MYSQL || tables.dbType == DbType.MARIADB)) -> "?"

            module.isR2dbcOrVertxSqlClient() && (tables.dbType == DbType.H2 || tables.dbType == DbType.POSTGRESQL) -> "$${++counter.index}"
            module.isR2dbcOrVertxSqlClient() && tables.dbType == DbType.MSSQL -> "@p${++counter.index}"
            else -> ":k${counter.index++}"
        }
}

public class Counter {
    internal var index = 0
}
