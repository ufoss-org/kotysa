package org.ufoss.kotysa

import kotlin.reflect.KClass

/**
 * Represents a Table
 *
 * @param T Entity type associated with this table
 */
public abstract class Table<T : Any>(private val tableName: String?) {

    internal lateinit var name: String

    private val columns = mutableSetOf<DbColumn<T, *>>()
    private lateinit var pk: PrimaryKey<T>

    @PublishedApi
    internal val foreignKeys = mutableSetOf<ForeignKey<T, *>>()

    protected fun primaryKey(
            vararg columns: ColumnNotNull<T, *>,
            pkName: String? = null
    ): PrimaryKey<T> {
        check(!::pk.isInitialized) {
            "Table must not declare more than one Primary Key"
        }
        return PrimaryKey(pkName, columns.toList())
    }

    protected fun <U : ColumnNotNull<T, *>> U.primaryKey(pkName: String? = null): U {
        check(!::pk.isInitialized) {
            "Table must not declare more than one Primary Key"
        }
        pk = PrimaryKey(pkName, listOf(this))
        return this
    }

    internal fun addColumn(column: DbColumn<T, *>) {
        require(!columns.any { col -> col.entityGetter == column.entityGetter }) {
            "Trying to map property \"${column.entityGetter}\" to multiple columns"
        }
        columns += column
    }

    internal fun initialize(kClass: KClass<*>): KotysaTable<T> {
        name = tableName ?: kClass.simpleName!!

        require(::pk.isInitialized) { "Table primary key is mandatory" }
        require(columns.isNotEmpty()) { "Table must declare at least one column" }

        // build KotysaColumns
        val kotysaColumnsMap = columns.associateWith { column ->
            KotysaColumnImpl(column, column.entityGetter, column.name, column.sqlType, column.isAutoIncrement,
                    column.isNullable, column.defaultValue, column.size)
        }

        // build Kotysa PK
        val kotysaPK = KotysaPrimaryKey(pk.name, pk.columns.map { column -> kotysaColumnsMap[column]!! })

        // build Kotysa FKs
        val kotysaFKs = foreignKeys.map { fk ->
            KotysaForeignKey(fk.referencedTable, fk.columns.map { column -> kotysaColumnsMap[column]!! }, fk.name)
        }

        @Suppress("UNCHECKED_CAST")
        val kotysaTable = KotysaTableImpl(kClass as KClass<T>, this, name!!, kotysaColumnsMap.values, kotysaPK,
                kotysaFKs)
        // associate table to all its columns
        kotysaTable.columns.forEach { c -> c.table = kotysaTable }
        return kotysaTable
    }
}
