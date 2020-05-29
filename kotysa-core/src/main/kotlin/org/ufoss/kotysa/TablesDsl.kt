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
		resolveFkReferencedColumn(allColumns, tables)

		// build foreign keys
		buildForeignKeys(allTables)

		return tables
	}

	private fun resolveFkReferencedColumn(allColumns: MutableMap<(Any) -> Any?, Column<*, *>>, tables: Tables) {
		allColumns.filterValues { column -> column.fkClass != null }
				.forEach { (_, column) ->
					val referencedTable = tables.getTable(column.fkClass!!)
					val referencedTablePK = referencedTable.primaryKey
					require(referencedTablePK is SinglePrimaryKey<*, *>) {
						"Only table with single column primary key is currently supported, ${referencedTable.name} is not"
					}
					column.fkColumn = referencedTablePK.column
				}
	}

	private fun buildForeignKeys(allTables: MutableMap<KClass<*>, Table<*>>) {
		val foreignKeyNames = mutableSetOf<String>()
		allTables.values.forEach { table ->
			val foreignKeys = mutableSetOf<ForeignKey>()
			// first loop with user provided FK names
			table.columns.values.mapNotNull { column -> column.fkName }
					.forEach { fkName ->
						require(!foreignKeyNames.contains(fkName)) {
							"Foreign key names must be unique, $fkName is duplicated"
						}
						foreignKeyNames.add(fkName)
					}

			// then complete loop on all foreign keys
			table.columns.filterValues { column -> column.fkColumn != null }
					.forEach { (_, column) ->
						foreignKeys.add(SingleForeignKey(column.fkName, column, column.fkColumn!!))
					}
			table.foreignKeys = foreignKeys
		}
	}
}
