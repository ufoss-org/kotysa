package org.ufoss.kotysa

public interface Table<T : Any>

/**
 * Represents a Table
 *
 * @param T Entity type associated with this table
 */
public abstract class AbstractTable<T : Any>(internal val tableName: String?) : Table<T>, Cloneable {
    internal lateinit var kotysaName: String
    internal val kotysaColumns = mutableSetOf<DbColumn<T, *>>()
    internal lateinit var kotysaPk: PrimaryKey<T, *>
    internal val kotysaForeignKeys = mutableSetOf<ForeignKey<T, *>>()
    internal var kotysaAlias: String? = null

    public override fun clone(): Any {
        return super.clone()
    }

    protected fun <U> primaryKey(
            vararg columns: U,
            pkName: String? = null
    ): PrimaryKey<T, *> where U : DbColumn<T, *>,
                              U : ColumnNotNull<T, *> {
        check(!::kotysaPk.isInitialized) {
            "Table must not declare more than one Primary Key"
        }
        return PrimaryKey(pkName, columns.toList()).also { primaryKey -> kotysaPk = primaryKey }
    }

    protected fun <U> U.primaryKey(pkName: String? = null)
            : U where U : DbColumn<T, *>,
                      U : ColumnNotNull<T, *> {
        check(!::kotysaPk.isInitialized) {
            "Table must not declare more than one Primary Key"
        }
        kotysaPk = PrimaryKey(pkName, listOf(this))
        return this
    }

    internal fun addColumn(column: DbColumn<T, *>) {
        require(!kotysaColumns.any { col -> col.entityGetter == column.entityGetter }) {
            "Trying to map property \"${column.entityGetter}\" to multiple columns"
        }
        kotysaColumns += column
    }

    internal fun isPkInitialized() = ::kotysaPk.isInitialized
}
