package org.ufoss.kotysa

public interface Table<T : Any>

/**
 * Represents a Table
 *
 * @param T Entity type associated with this table
 */
public abstract class AbstractTable<T : Any>(internal val tableName: String?) : Table<T> {

    internal lateinit var name: String

    internal val columns = mutableSetOf<DbColumn<T, *>>()
    internal lateinit var pk: PrimaryKey<T, *>
    internal val foreignKeys = mutableSetOf<ForeignKey<T, *>>()

    protected fun <U> primaryKey(
            vararg columns: U,
            pkName: String? = null
    ): PrimaryKey<T, *> where U : DbColumn<T, *>,
                              U : ColumnNotNull<T, *> {
        check(!::pk.isInitialized) {
            "Table must not declare more than one Primary Key"
        }
        return PrimaryKey(pkName, columns.toList()).also { primaryKey -> pk = primaryKey }
    }

    protected fun <U> U.primaryKey(pkName: String? = null)
            : U where U : DbColumn<T, *>,
                      U : ColumnNotNull<T, *> {
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

@Suppress("UNCHECKED_CAST")
internal fun <T : Any> Table<T>.getKotysaTable(availableTables: Map<Table<*>, KotysaTable<*>>): KotysaTable<T> {
    return requireNotNull(availableTables[this]) { "Requested table \"$this\" is not mapped" } as KotysaTable<T>
}
