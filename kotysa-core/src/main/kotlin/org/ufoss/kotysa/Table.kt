package org.ufoss.kotysa

import org.ufoss.kotysa.columns.KotysaColumnNotNull
import org.ufoss.kotysa.columns.KotysaColumnNullable
import kotlin.reflect.KClass

/**
 * Represents a Table
 *
 * @param T Entity type associated with this table
 */
public abstract class Table<T : Any> {
    protected open lateinit var name: String
    
    internal val columns = mutableSetOf<KotysaColumn<T, *>>()
    internal lateinit var pk: PrimaryKey<T>
    @PublishedApi
    internal val foreignKeys = mutableSetOf<ForeignKey<T, *>>()

    public fun primaryKey(
            vararg columns: KotysaColumnNotNull<T, *>,
            pkName: String? = null
    ): PrimaryKey<T> = PrimaryKey(pkName, columns.toList())

    protected fun <U : KotysaColumnNotNull<T, *>> U.primaryKey(pkName: String? = null): U {
        check(!::pk.isInitialized) {
            "Table must not declare more than one Primary Key"
        }
        pk = PrimaryKey(pkName, listOf(this))
        return this
    }

    protected fun <V : Any> foreignKey(
            referencedTable: Table<V>,
            vararg columns: KotysaColumn<T, *>,
            fkName: String? = null
    ) {
        foreignKeys.add(ForeignKey(referencedTable, columns.toList(), fkName))
    }

    protected fun <U : KotysaColumnNotNull<T, *>, V : Any> U.foreignKey(referencedTable: Table<V>, fkName: String? = null): U {
        foreignKeys.add(ForeignKey(referencedTable, listOf(this), fkName))
        return this
    }

    protected inline fun <U : KotysaColumnNullable<T, *>, reified V : Any> U.foreignKey(referencedTable: Table<V>, fkName: String? = null): U {
        foreignKeys.add(ForeignKey(referencedTable, listOf(this), fkName))
        return this
    }

    internal fun addColumn(column: KotysaColumn<T, *>) {
        require(!columns.contains(column.entityGetter)) {
            "Trying to map property \"${column.entityGetter}\" to multiple columns"
        }
        columns += column
    }

    internal fun initialize(kClass: KClass<*>): KotysaTable<T> {
        if (!::name.isInitialized) {
            name = kClass.simpleName!!
        }
        require(::pk.isInitialized) { "Table primary key is mandatory" }
        require(columns.isNotEmpty()) { "Table must declare at least one column" }
        @Suppress("UNCHECKED_CAST")
        val kotysaTable = KotysaTableImpl(kClass as KClass<T>, this, name, columns, pk, foreignKeys)
        // associate table to all its columns
        columns.forEach { c -> c.table = kotysaTable }
        return kotysaTable
    }
}
