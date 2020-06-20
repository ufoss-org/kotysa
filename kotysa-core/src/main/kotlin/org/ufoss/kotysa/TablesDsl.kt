/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import kotlin.reflect.KClass


@KotysaMarker
public abstract class TablesDsl<T : TablesDsl<T, U>, U : TableDsl<*, *>> protected constructor(private val init: T.() -> Unit) {

    private val allTables = mutableMapOf<KClass<*>, Table<*>>()
    private val allColumns = mutableMapOf<(Any) -> Any?, Column<*, *>>()

    @PublishedApi
    internal fun <T : Any> table(tableClass: KClass<T>, dsl: U.() -> Unit) {
        check(!allTables.containsKey(tableClass)) {
            "Trying to map entity class \"${tableClass.qualifiedName}\" to multiple tables"
        }
        val table = initializeTable(tableClass, dsl)
        allTables[tableClass] = table
        @Suppress("UNCHECKED_CAST")
        allColumns.putAll(table.columns as Map<out (Any) -> Any?, Column<*, *>>)
    }

    protected abstract fun <T : Any> initializeTable(tableClass: KClass<T>, dsl: U.() -> Unit): Table<*>

    internal fun initialize(initialize: T, dbType: DbType): Tables {
        init(initialize)
        require(allTables.isNotEmpty()) { "Tables must declare at least one table" }
        val tables = Tables(allTables, allColumns, dbType)

        // resolve foreign keys to referenced primary key column
        resolveFkReferences(tables)

        return tables
    }

    /**
     * Fill lateinit foreign key data after tables are built
     */
    @Suppress("UNCHECKED_CAST")
    private fun resolveFkReferences(tables: Tables) {
        tables.allTables.values
                .flatMap { table -> table.foreignKeys }
                .forEach { foreignKey ->
                    val referencedTable = tables.getTable(foreignKey.referencedClass)
                    foreignKey.referencedTable = referencedTable
                    if (foreignKey.properties != null) {
                        val columns = foreignKey.properties
                                .map { columnProperty ->
                                    allColumns[columnProperty.getter] ?: throw IllegalArgumentException(
                                            "Column property ${columnProperty.getter} is not declared as Column")
                                }
                        foreignKey.columns = columns
                    }

                    // find primaryKey if no referencedProperties
                    if (foreignKey.referencedProperties.isEmpty()) {
                        foreignKey.referencedColumns = referencedTable.primaryKey.columns
                    } else {
                        val referencedColumns = foreignKey.referencedProperties
                                .map { columnProperty ->
                                    allColumns[columnProperty.getter] ?: throw IllegalArgumentException(
                                            "Column property ${columnProperty.getter} is not declared as Column")
                                }
                        foreignKey.referencedColumns = referencedColumns
                    }
                }
    }
}
