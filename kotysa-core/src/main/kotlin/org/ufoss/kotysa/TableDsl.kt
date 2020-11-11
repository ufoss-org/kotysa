/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import org.ufoss.kotysa.columns.Column
import org.ufoss.kotysa.columns.ColumnNotNull
import org.ufoss.kotysa.columns.ColumnNullable
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
            vararg columns: ColumnNotNull<T, *>,
            pkName: String? = null
    ) {
        check(!::pk.isInitialized) {
            "Table must not declare more than one Primary Key"
        }
        pk = PrimaryKey(pkName, columns.toList())
    }

    public fun ColumnNotNull<T, *>.primaryKey(pkName: String? = null): ColumnNotNull<T, *> {
        check(!::pk.isInitialized) {
            "Table must not declare more than one Primary Key"
        }
        pk = PrimaryKey(pkName, listOf(this))
        return this
    }

    public inline fun <reified V : Any> foreignKey(
            vararg columns: Column<T, *>,
            fkName: String? = null
    ) {
        foreignKeys.add(ForeignKey(V::class, columns.toList(), fkName))
    }

    public inline fun <reified V : Any> ColumnNotNull<T, *>.foreignKey(fkName: String? = null): ColumnNotNull<T, *> {
        foreignKeys.add(ForeignKey(V::class, listOf(this), fkName))
        return this
    }

    public inline fun <reified V : Any> ColumnNullable<T, *>.foreignKey(fkName: String? = null): ColumnNullable<T, *> {
        foreignKeys.add(ForeignKey(V::class, listOf(this), fkName))
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
    internal fun initialize(initialize: U): KotysaTable<*> {
        init(initialize)
        if (!::name.isInitialized) {
            name = tableClass.simpleName!!
        }
        require(::pk.isInitialized) { "Table primary key is mandatory" }
        require(columns.isNotEmpty()) { "Table must declare at least one column" }
        val table = KotysaTableImpl(tableClass, name, columns, pk, foreignKeys)
        // associate table to all its columns
        columns.forEach { (_, c) -> c.table = table }
        return table
    }
}
