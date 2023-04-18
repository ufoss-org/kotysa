/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import org.ufoss.kotysa.columns.AbstractColumn
import org.ufoss.kotysa.columns.AbstractDbColumn
import org.ufoss.kotysa.columns.TsvectorColumn
import org.ufoss.kotysa.h2.H2Table
import org.ufoss.kotysa.mariadb.MariadbTable
import org.ufoss.kotysa.mssql.MssqlTable
import org.ufoss.kotysa.mysql.MysqlTable
import org.ufoss.kotysa.oracle.OracleTable
import org.ufoss.kotysa.postgresql.PostgresqlTable
import org.ufoss.kotysa.sqlite.SqLiteTable
import kotlin.reflect.KClass
import kotlin.reflect.full.allSupertypes

/**
 * Supported Database Choice
 */
public abstract class DbTypeChoiceCommon {

    private fun fillTables(dbType: DbType, tables: Array<out AbstractTable<*>>)
            : Pair<Map<Table<*>, KotysaTable<*>>, Map<Column<*, *>, KotysaColumn<*, *>>> {
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
                        DbType.ORACLE -> OracleTable::class == type.classifier
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
        return Pair(allTables, allColumns)
    }

    internal fun buildH2Tables(tables: Array<out AbstractTable<*>>): H2Tables {
        val pair = fillTables(DbType.H2, tables)
        return H2Tables(pair.first, pair.second)
    }

    internal fun buildMysqlTables(tables: Array<out AbstractTable<*>>): MysqlTables {
        val pair = fillTables(DbType.MYSQL, tables)
        return MysqlTables(pair.first, pair.second)
    }

    internal fun buildPostgresqlTables(tables: Array<out AbstractTable<*>>): PostgresqlTables {
        val pair = fillTables(DbType.POSTGRESQL, tables)
        return PostgresqlTables(pair.first, pair.second)
    }

    internal fun buildMssqlTables(tables: Array<out AbstractTable<*>>): MssqlTables {
        val pair = fillTables(DbType.MSSQL, tables)
        return MssqlTables(pair.first, pair.second)
    }

    internal fun buildMariadbTables(tables: Array<out AbstractTable<*>>): MariadbTables {
        val pair = fillTables(DbType.MARIADB, tables)
        return MariadbTables(pair.first, pair.second)
    }

    internal fun buildOracleTables(tables: Array<out AbstractTable<*>>): OracleTables {
        val pair = fillTables(DbType.ORACLE, tables)
        return OracleTables(pair.first, pair.second)
    }

    internal fun buildSqLiteTables(tables: Array<out AbstractTable<*>>): SqLiteTables {
        val pair = fillTables(DbType.SQLITE, tables)
        return SqLiteTables(pair.first, pair.second)
    }

    private fun initializeTable(table: AbstractTable<Any>, tableClass: KClass<Any>): KotysaTable<*> {
        // define table name = provided name or else mapping class simpleName
        table.kotysaName = table.tableName ?: table::class.simpleName!!

        require(table.isPkInitialized()) { "Table primary key is mandatory" }
        require(table.kotysaColumns.isNotEmpty()) { "Table must declare at least one column" }

        // build KotysaColumns
        val kotysaColumnsMap = linkedMapOf<Column<Any, *>, KotysaColumn<Any, *>>()
        // 1) all DbColumns
        table.kotysaColumns
            .filterIsInstance<AbstractDbColumn<Any, *>>()
            .forEach { column ->
                column.name = buildColumnName(column, table)
                @Suppress("UNCHECKED_CAST")
                val kotysaColumn = KotysaColumnDb(
                    column,
                    column.entityGetter,
                    column.entityGetter.toCallable().returnType.classifier as KClass<Any>,
                    column.name,
                    column.sqlType,
                    column.isAutoIncrement,
                    column.isNullable,
                    column.defaultValue,
                    column.size,
                    column.scale,
                    column.identity,
                )
                kotysaColumnsMap[column] = kotysaColumn
            }

        // all TsvectorColumns
        table.kotysaColumns
            .filterIsInstance<TsvectorColumn<Any>>()
            .forEach { column ->
                column.name = buildColumnName(column, table)
                val kotysaColumn = KotysaColumnTsvector(
                    column,
                    column.name,
                    column.tsvectorType,
                    column.columns.map { col ->
                        kotysaColumnsMap[col] ?: throw IllegalArgumentException(
                            "Column ${buildColumnName(col, table)} is not mapped"
                        )
                    }
                )
                kotysaColumnsMap[column] = kotysaColumn
            }

        val kotysaTable = KotysaTableImpl(
            tableClass, table, table.kotysaName, kotysaColumnsMap.values.toList(),
            table.kotysaPk, table.kotysaForeignKeys, table.kotysaIndexes
        )
        // associate table to all its columns
        kotysaTable.columns.forEach { c -> c.table = kotysaTable }
        return kotysaTable
    }

    /**
     * If the name of the column is null, use the 'Table mapping' property name
     */
    private fun buildColumnName(column: AbstractColumn<Any, *>, table: AbstractTable<Any>): String =
        column.columnName
            ?: table::class.members
                .first { callable ->
                    Column::class.java.isAssignableFrom((callable.returnType.classifier as KClass<*>).java)
                            && column == callable.call(table)
                }.name
}

public expect object DbTypeChoice : DbTypeChoiceCommon

/**
 * Choose the database's Type
 *
 * @see Tables
 * @see DbTypeChoice
 */
public fun tables(): DbTypeChoice = DbTypeChoice