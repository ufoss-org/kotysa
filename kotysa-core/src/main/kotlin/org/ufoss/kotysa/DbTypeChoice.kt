/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import org.ufoss.kotysa.columns.KotysaColumn
import org.ufoss.kotysa.h2.H2Table
import org.ufoss.kotysa.sqlite.SqLiteTable
import kotlin.reflect.KClass

/**
 * Supported Database Choice
 */
public object DbTypeChoice {

    /**
     * Configure Functional Table Mapping support for H2
     * @sample org.ufoss.kotysa.sample.h2Tables
     */
    public fun h2(vararg tables: H2Table<*>): Tables = fillTables(DbType.H2, *tables)

    /**
     * Configure Functional Table Mapping support for SqLite
     * @sample org.ufoss.kotysa.sample.sqLiteTables
     */
    public fun sqlite(vararg tables: SqLiteTable<*>): Tables = fillTables(DbType.SQLITE, *tables)

    private fun fillTables(dbType: DbType, vararg tables: Table<*>): Tables {
        require(tables.isNotEmpty()) { "Tables must declare at least one table" }

        val allTables = mutableSetOf<KotysaTable<*>>()
        val allColumns = mutableSetOf<KotysaColumn<*, *>>()
        for (table in tables) {
            val tableClass = table::class.supertypes
                    .first { type -> H2Table::class == type.classifier }
                    .arguments[0].type!!.classifier as KClass<*>
            check(!allTables.map { kotysaTable -> kotysaTable.tableClass }.contains(tableClass)) {
                "Trying to map entity class \"${tableClass.qualifiedName}\" to multiple tables"
            }
            val kotysaTable = table.initialize(tableClass)
            allTables += kotysaTable
            @Suppress("UNCHECKED_CAST")
            allColumns.addAll(table.columns)
        }
        val tablesModel = Tables(allTables, allColumns, dbType)
        // resolve foreign keys to referenced primary key column
        resolveFkReferences(tablesModel)
        return tablesModel
    }

    /**
     * Fill lateinit foreign key data after tables are built
     */
    @Suppress("UNCHECKED_CAST")
    private fun resolveFkReferences(tables: Tables) {
        tables.allTables.values
                .flatMap { table -> table.foreignKeys }
                .forEach { foreignKey ->
                    val referencedKotysaTable = tables.getTable(foreignKey.referencedTable)
                    foreignKey.referencedKotysaTable = referencedKotysaTable
                    // find primaryKey of referenced table
                    foreignKey.referencedColumns = referencedKotysaTable.primaryKey.columns
                }
    }

    /*
    /**
     * Configure Functional Table Mapping support for SqLite
     * @sample org.ufoss.kotysa.sample.sqLiteTables
     * @see SqLiteTablesDsl
     */
    public fun sqlite(dsl: SqLiteTablesDsl.() -> Unit): Tables {
        val tablesDsl = SqLiteTablesDsl(dsl, DbType.SQLITE)
        return tablesDsl.initialize(tablesDsl)
    }

    /**
     * Configure Functional Table Mapping support for PostgreSQL
     * @sample org.ufoss.kotysa.sample.postgresqlTables
     * @see PostgresqlTablesDsl
     */
    public fun postgresql(dsl: PostgresqlTablesDsl.() -> Unit): Tables {
        val tablesDsl = PostgresqlTablesDsl(dsl, DbType.POSTGRESQL)
        return tablesDsl.initialize(tablesDsl)
    }

    /**
     * Configure Functional Table Mapping support for MySQL
     * @sample org.ufoss.kotysa.sample.mysqlTables
     * @see MysqlTablesDsl
     */
    public fun mysql(dsl: MysqlTablesDsl.() -> Unit): Tables {
        val tablesDsl = MysqlTablesDsl(dsl, DbType.MYSQL)
        return tablesDsl.initialize(tablesDsl)
    }*/
}

/**
 * Choose the database's Type
 *
 * @see TablesDsl
 * @see DbTypeChoice
 */
public fun tables(): DbTypeChoice = DbTypeChoice
