package org.ufoss.kotysa

/**
 * Represents a Table
 *
 * @param T Entity type associated with this table
 */
public abstract class Table<T : Any>(internal val tableName: String?) {

    internal lateinit var name: String

    internal val columns = mutableSetOf<DbColumn<T, *>>()
    internal lateinit var pk: PrimaryKey<T>
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

    internal fun isPkInitialized() = ::pk.isInitialized
}
