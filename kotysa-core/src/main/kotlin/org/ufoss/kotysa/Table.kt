package org.ufoss.kotysa

public interface Table<T : Any>

/**
 * Represents a Table
 *
 * @param T Entity type associated with this table
 */
public abstract class AbstractTable<T : Any>(internal val tableName: String?) : Table<T> {
    internal lateinit var kotysaName: String
    internal val kotysaColumns = mutableSetOf<DbColumn<T, *>>()
    internal lateinit var kotysaPk: PrimaryKey<T>
    internal val kotysaForeignKeys = mutableSetOf<ForeignKey<T, *>>()
    internal val kotysaIndexes = mutableSetOf<Index<T>>()

    protected fun primaryKey(
        columns: Set<DbColumn<T, *>>,
        pkName: String? = null,
    ): PrimaryKey<T> {
        check(!::kotysaPk.isInitialized) {
            "Table must not declare more than one Primary Key"
        }
        return PrimaryKey(pkName, columns).also { primaryKey -> kotysaPk = primaryKey }
    }

    protected fun primaryKey(vararg columns: DbColumn<T, *>): PrimaryKey<T> = primaryKey(columns.toSet())

    protected fun <U> U.primaryKey(pkName: String? = null)
            : U where U : DbColumn<T, *>,
                      U : ColumnNotNull<T, *> {
        check(!isPkInitialized()) {
            "Table must not declare more than one Primary Key"
        }
        kotysaPk = PrimaryKey(pkName, setOf(this))
        return this
    }

    /*protected fun <V : Any> foreignKey(
            referencedTable: H2Table<V>,
            vararg columns: DbColumn<T, *>,
            fkName: String? = null
    ) {
        foreignKeys.add(ForeignKey(referencedTable, columns.toList(), fkName))
    }*/

    protected fun <U : DbColumn<T, *>, V : Any> U.foreignKey(references: DbColumn<V, *>, fkName: String? = null): U =
        this.also {
            kotysaForeignKeys.add(ForeignKey(mapOf(this to references), fkName))
        }

    protected fun index(
        columns: Set<DbColumn<T, *>>,
        type: IndexType? = null,
        indexName: String? = null,
    ): Index<T> = Index(columns, type, indexName).apply { kotysaIndexes.add(this) }

    protected fun index(vararg columns: DbColumn<T, *>): Index<T> = index(columns.toSet())

    protected fun <U : DbColumn<T, *>> U.unique(indexName: String? = null): U =
        this.also {
            kotysaIndexes.add(Index(setOf(this), IndexType.UNIQUE, indexName))
        }

    internal fun addColumn(column: DbColumn<T, *>) {
        require(!kotysaColumns.any { col -> col.entityGetter == column.entityGetter }) {
            "Trying to map property \"${column.entityGetter}\" to multiple columns"
        }
        kotysaColumns += column
    }

    internal fun isPkInitialized() = ::kotysaPk.isInitialized
}
