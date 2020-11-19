/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import org.ufoss.kotysa.h2.H2Table
import org.ufoss.kotysa.mysql.MysqlTable
import org.ufoss.kotysa.postgresql.PostgresqlTable
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

        val allTables = mutableMapOf<Table<*>, KotysaTable<*>>()
        val allColumns = mutableMapOf<Column<*, *>, KotysaColumn<*, *>>()
        for (table in tables) {
            val tableClass = requireNotNull(table::class.supertypes
                    .firstOrNull { type ->
                        when (dbType) {
                            DbType.SQLITE -> SqLiteTable::class == type.classifier
                            DbType.MYSQL -> MysqlTable::class == type.classifier
                            DbType.POSTGRESQL -> PostgresqlTable::class == type.classifier
                            DbType.H2 -> H2Table::class == type.classifier
                        }
                    }
            ) { "Table $table should be a subclass of the platform $dbType Table" }
                    .arguments[0].type!!.classifier as KClass<*>
            check(!allTables.values.map { kotysaTable -> kotysaTable.tableClass }.contains(tableClass)) {
                "Trying to map entity class \"${tableClass.qualifiedName}\" to multiple tables"
            }
            @Suppress("UNCHECKED_CAST")
            val kotysaTable = initializeTable(table as Table<Any>, tableClass as KClass<Any>)
            allTables[kotysaTable.table] = kotysaTable
            allColumns.putAll(kotysaTable.columns.associateBy { kotysaColumn -> kotysaColumn.column })
        }
        val tablesModel = Tables(allTables, allColumns, dbType)
        // resolve foreign keys to referenced primary key column
        resolveFkReferences(tablesModel)
        return tablesModel
    }

    private fun initializeTable(table: Table<Any>, tableClass: KClass<Any>): KotysaTable<*> {
        val tableName = table.name ?: table::class.simpleName!!

        require(table.isPkInitialized()) { "Table primary key is mandatory" }
        require(table.columns.isNotEmpty()) { "Table must declare at least one column" }

        // build KotysaColumns
        val kotysaColumnsMap = table.columns.associateWith { column ->

            val columnName = column.name
                    ?: table::class.members // If the name of the column is null, use the 'Table mapping' property name
                            .first { callable ->
                                Column::class.java.isAssignableFrom((callable.returnType.classifier as KClass<*>).java)
                                        && column == callable.call(table)
                            }.name

            KotysaColumnImpl(column, column.entityGetter, columnName, column.sqlType, column.isAutoIncrement,
                    column.isNullable, column.defaultValue, column.size)
        }

        // build Kotysa PK
        val kotysaPK = KotysaPrimaryKey(table.pk.name, table.pk.columns.map { column -> kotysaColumnsMap[column]!! })

        // build Kotysa FKs
        val kotysaFKs = table.foreignKeys.map { fk ->
            KotysaForeignKey(fk.referencedTable, fk.columns.map { column -> kotysaColumnsMap[column]!! }, fk.name)
        }

        @Suppress("UNCHECKED_CAST")
        val kotysaTable = KotysaTableImpl(tableClass, table, tableName, kotysaColumnsMap.values, kotysaPK,
                kotysaFKs)
        // associate table to all its columns
        kotysaTable.columns.forEach { c -> c.table = kotysaTable }
        return kotysaTable
    }

    /**
     * Fill lateinit foreign key data after tables are built
     */
    private fun resolveFkReferences(tables: Tables) {
        tables.allTables.values
                .flatMap { table -> table.foreignKeys }
                .forEach { foreignKey ->
                    val referencedKotysaTable = tables.getTable(foreignKey.referencedTable)
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
 * @see Tables
 * @see DbTypeChoice
 */
public fun tables(): DbTypeChoice = DbTypeChoice
