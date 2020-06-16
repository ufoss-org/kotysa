/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import kotlin.reflect.KClass


@KotysaMarker
public abstract class TableDsl<T : Any, U : TableDsl<T, U>>(
        private val init: U.() -> Unit,
        private val tableClass: KClass<T>
) {

    public lateinit var name: String
    private val columns = mutableMapOf<(T) -> Any?, Column<T, *>>()
    @PublishedApi
    internal val foreignKeys = mutableSetOf<ForeignKey<T, *>>()
    public lateinit var primaryKey: PrimaryKey

    public inline fun <reified V : Any> foreignKey(
            noinline dsl: ForeignKeyDsl<T, V>.(TableColumnPropertyProvider) -> ForeignKeyBuilder<T, V>
    ) {
        val foreignKeyDsl = ForeignKeyDsl(dsl, V::class)
        val foreignKey = foreignKeyDsl.initialize()
        foreignKeys.add(foreignKey)
    }

    public inline fun <reified V : Any> Column<T, *>.foreignKey(name: String? = null) {
        val foreignKey = ForeignKey<T, V>(V::class, setOf(), name, null)
        foreignKey.columns = listOf(this)
        foreignKeys.add(foreignKey)
    }

    protected fun addColumn(column: Column<T, *>) {
        require(!columns.containsKey(column.entityGetter)) {
            "Trying to map property \"${column.entityGetter}\" to multiple columns"
        }
        require(tableClass.members.contains(column.entityGetter.toCallable())) {
            "Trying to map property \"${column.entityGetter}\", which is not a property of entity class \"${tableClass.qualifiedName}\""
        }
        if (column.isPrimaryKey) {
            check(!::primaryKey.isInitialized) {
                "Table must not declare more than one Primary Key"
            }
            primaryKey = SinglePrimaryKey(column.pkName, column)
        }
        columns[column.entityGetter] = column
    }

    @PublishedApi
    internal fun initialize(initialize: U): Table<*> {
        init(initialize)
        if (!::name.isInitialized) {
            name = tableClass.simpleName!!
        }
        require(::primaryKey.isInitialized) { "Table primary key is mandatory" }
        require(columns.isNotEmpty()) { "Table must declare at least one column" }
        val table = TableImpl(tableClass, name, columns, primaryKey, foreignKeys)
        // associate table to all its columns
        columns.forEach { (_, c) -> c.table = table }
        return table
    }
}
