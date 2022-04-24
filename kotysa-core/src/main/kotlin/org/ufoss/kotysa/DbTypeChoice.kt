/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import org.ufoss.kotysa.h2.H2Table
import org.ufoss.kotysa.mariadb.MariadbTable
import org.ufoss.kotysa.mssql.MssqlTable
import org.ufoss.kotysa.mysql.MysqlTable
import org.ufoss.kotysa.postgresql.PostgresqlTable
import org.ufoss.kotysa.sqlite.SqLiteTable
import kotlin.reflect.KClass
import kotlin.reflect.full.allSupertypes

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
     * Configure Functional Table Mapping support for MySQL
     * @sample org.ufoss.kotysa.sample.mysqlTables
     */
    public fun mysql(vararg tables: MysqlTable<*>): Tables = fillTables(DbType.MYSQL, *tables)

    /**
     * Configure Functional Table Mapping support for PostgreSQL
     * @sample org.ufoss.kotysa.sample.postgresqlTables
     */
    public fun postgresql(vararg tables: PostgresqlTable<*>): Tables = fillTables(DbType.POSTGRESQL, *tables)

    /**
     * Configure Functional Table Mapping support for Microsoft SQL Server
     * @sample org.ufoss.kotysa.sample.mssqlTables
     */
    public fun mssql(vararg tables: MssqlTable<*>): Tables = fillTables(DbType.MSSQL, *tables)

    /**
     * Configure Functional Table Mapping support for MariaDB
     * @sample org.ufoss.kotysa.sample.mariadbTables
     */
    public fun mariadb(vararg tables: MariadbTable<*>): Tables = fillTables(DbType.MARIADB, *tables)

    /**
     * Configure Functional Table Mapping support for SqLite
     * @sample org.ufoss.kotysa.sample.sqLiteTables
     */
    public fun sqlite(vararg tables: SqLiteTable<*>): Tables = fillTables(DbType.SQLITE, *tables)

    private fun fillTables(dbType: DbType, vararg tables: AbstractTable<*>): Tables {
        require(tables.isNotEmpty()) { "Tables must declare at least one table" }

        val allTables = mutableMapOf<Table<*>, KotysaTable<*>>()
        val allColumns = mutableMapOf<Column<*, *>, KotysaColumn<*, *>>()
        for (table in tables) {
            val tableClass = requireNotNull(table::class.allSupertypes
                    .firstOrNull { type ->
                        when (dbType) {
                            DbType.SQLITE -> SqLiteTable::class == type.classifier
                            DbType.MYSQL -> MysqlTable::class == type.classifier
                            DbType.POSTGRESQL -> PostgresqlTable::class == type.classifier
                            DbType.H2 -> H2Table::class == type.classifier
                            DbType.MSSQL -> MssqlTable::class == type.classifier
                            DbType.MARIADB -> MariadbTable::class == type.classifier
                        }
                    }
            ) { "Table $table should be a subclass of the platform $dbType Table" }
                    .arguments[0].type!!.classifier as KClass<*>
            check(!allTables.values.map { kotysaTable -> kotysaTable.tableClass }.contains(tableClass)) {
                "Trying to map entity class \"${tableClass.qualifiedName}\" to multiple tables"
            }
            @Suppress("UNCHECKED_CAST")
            val kotysaTable = initializeTable(table as AbstractTable<Any>, tableClass as KClass<Any>)
            allTables[kotysaTable.table] = kotysaTable
            allColumns.putAll(kotysaTable.columns.associateBy { kotysaColumn -> kotysaColumn.column })
        }
        return Tables(allTables, allColumns, dbType)
    }

    private fun initializeTable(table: AbstractTable<Any>, tableClass: KClass<Any>): KotysaTable<*> {
        // define table name = provided name or else mapping class simpleName
        table.kotysaName = table.tableName ?: table::class.simpleName!!

        require(table.isPkInitialized()) { "Table primary key is mandatory" }
        require(table.kotysaColumns.isNotEmpty()) { "Table must declare at least one column" }

        // build KotysaColumns
        val kotysaColumnsMap = linkedMapOf<Column<Any, *>, KotysaColumn<Any, *>>()
        table.kotysaColumns.forEach { column ->
            // If the name of the column is null, use the 'Table mapping' property name
            column.name = column.columnName
                    ?: table::class.members
                            .first { callable ->
                                Column::class.java.isAssignableFrom((callable.returnType.classifier as KClass<*>).java)
                                        && column == callable.call(table)
                            }.name

            @Suppress("UNCHECKED_CAST")
            val kotysaColumn = KotysaColumnImpl(column, column.entityGetter, column.entityGetter.toCallable().returnType.classifier as KClass<Any>, column.name, column.sqlType, column.isAutoIncrement,
                    column.isNullable, column.defaultValue, column.size)
            kotysaColumnsMap[column] = kotysaColumn
        }

        val kotysaTable = KotysaTableImpl(tableClass, table, table.kotysaName, kotysaColumnsMap.values.toList(), table.kotysaPk, table.kotysaForeignKeys)
        // associate table to all its columns
        kotysaTable.columns.forEach { c -> c.table = kotysaTable }
        return kotysaTable
    }
}

/**
 * Choose the database's Type
 *
 * @see Tables
 * @see DbTypeChoice
 */
public fun tables(): DbTypeChoice = DbTypeChoice
