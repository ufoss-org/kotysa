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
    private lateinit var pk: PrimaryKey<T>

    public fun primaryKey(
            dsl: PrimaryKeyDsl<T>.(TableColumnPropertyProvider) -> PrimaryKey<T>
    ) {
        pk = PrimaryKeyDsl(dsl).initialize()
    }

    public inline fun <reified V : Any> foreignKey(
            noinline dsl: ForeignKeyDsl<T, V>.(TableColumnPropertyProvider) -> ForeignKeyBuilder<T, V>
    ) {
        foreignKeys.add(ForeignKeyDsl(dsl, V::class).initialize())
    }

    public inline fun <reified V : Any> Column<T, *>.foreignKey(fkName: String? = null): Column<T, *> {
        val foreignKey = ForeignKey<T, V>(V::class, listOf(), fkName, null)
        foreignKey.columns = listOf(this)
        foreignKeys.add(foreignKey)
        return this
    }

    public fun ColumnNotNull<T, *>.primaryKey(pkName: String? = null): ColumnNotNull<T, *> {
        check(!::pk.isInitialized) {
            "Table must not declare more than one Primary Key"
        }
        val primaryKey = PrimaryKey<T>(pkName, null)
        primaryKey.columns = listOf(this)
        pk = primaryKey
        return this
    }

    protected fun addColumn(column: Column<T, *>) {
        require(!columns.containsKey(column.entityGetter)) {
            "Trying to map property \"${column.entityGetter}\" to multiple columns"
        }
        require(tableClass.members.contains(column.entityGetter.toCallable())) {
            "Trying to map property \"${column.entityGetter}\", which is not a property of entity class \"${tableClass.qualifiedName}\""
        }
        columns[column.entityGetter] = column
    }

    @PublishedApi
    internal fun initialize(initialize: U): Table<*> {
        init(initialize)
        if (!::name.isInitialized) {
            name = tableClass.simpleName!!
        }
        require(::pk.isInitialized) { "Table primary key is mandatory" }
        require(columns.isNotEmpty()) { "Table must declare at least one column" }
        val table = TableImpl(tableClass, name, columns, pk, foreignKeys)
        // associate table to all its columns
        columns.forEach { (_, c) -> c.table = table }
        return table
    }
}
