/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import org.ufoss.kotysa.columns.Column
import kotlin.reflect.KClass


@KotysaMarker
public abstract class TablesDsl<T : TablesDsl<T, U>, U : TableDsl<*, *>> protected constructor(
        private val init: T.() -> Unit,
        private val dbType: DbType
) {

    private val allTables = mutableMapOf<KClass<*>, KotysaTable<*>>()
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

    protected abstract fun <T : Any> initializeTable(tableClass: KClass<T>, dsl: U.() -> Unit): KotysaTable<*>

    internal fun initialize(initialize: T): Tables {
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
                    // find primaryKey of referenced table
                    foreignKey.referencedColumns = referencedTable.primaryKey.columns
                }
    }
}
