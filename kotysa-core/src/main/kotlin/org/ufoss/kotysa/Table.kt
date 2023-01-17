package org.ufoss.kotysa

import org.ufoss.kotysa.columns.*

public interface Table<T : Any>

/**
 * Represents a Table
 *
 * @param T Entity type associated with this table
 */
public abstract class AbstractTable<T : Any> internal constructor(internal val tableName: String?) : Table<T> {
    internal lateinit var kotysaName: String
    internal val kotysaColumns = mutableSetOf<AbstractColumn<T, *>>()
    internal lateinit var kotysaPk: PrimaryKey<T>
    internal val kotysaForeignKeys = mutableSetOf<ForeignKey<T, *>>()
    internal val kotysaIndexes = mutableSetOf<Index<T>>()

    protected fun primaryKey(
        columns: Set<AbstractDbColumn<T, *>>,
        pkName: String? = null,
    ): PrimaryKey<T> {
        check(!::kotysaPk.isInitialized) {
            "Table must not declare more than one Primary Key"
        }
        return PrimaryKey(pkName, columns).also { primaryKey -> kotysaPk = primaryKey }
    }

    protected fun primaryKey(vararg columns: AbstractDbColumn<T, *>): PrimaryKey<T> = primaryKey(columns.toSet())

    protected fun <U> U.primaryKey(pkName: String? = null)
            : U where U : AbstractDbColumn<T, *>,
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

    protected fun <U : AbstractDbColumn<T, *>, V : Any> U.foreignKey(
        references: AbstractDbColumn<V, *>,
        fkName: String? = null,
    ): U =
        this.also {
            kotysaForeignKeys.add(ForeignKey(mapOf(this to references), fkName))
        }

    protected fun index(
        columns: Set<AbstractDbColumn<T, *>>,
        type: IndexType? = null,
        indexName: String? = null,
    ): Index<T> = Index(columns, type, indexName).apply { kotysaIndexes.add(this) }

    protected fun index(vararg columns: AbstractDbColumn<T, *>): Index<T> = index(columns.toSet())

    protected fun <U : AbstractDbColumn<T, *>> U.unique(indexName: String? = null): U =
        this.also {
            kotysaIndexes.add(Index(setOf(this), IndexType.UNIQUE, indexName))
        }

    internal fun addColumn(column: AbstractColumn<T, *>) {
        if (column is AbstractDbColumn<T, *>) {
            require(!kotysaColumns
                .filterIsInstance<AbstractDbColumn<T, *>>()
                .any { col -> col.entityGetter == column.entityGetter }) {
                "Trying to map property \"${column.entityGetter}\" to multiple columns"
            }
        }
        kotysaColumns += column
    }

    internal fun isPkInitialized() = ::kotysaPk.isInitialized
}

/**
 * Represents a Table
 *
 * @param T Entity type associated with this table
 */
public abstract class AbstractCommonTable<T : Any> internal constructor(tableName: String?) :
    AbstractTable<T>(tableName) {
    protected fun <U> U.identity(): IntDbIdentityColumnNotNull<T>
            where U : AbstractDbColumn<T, Int>,
                  U : IntColumnNullable<T> =
        IntDbIdentityColumnNotNull(this, Identity()).also { addIdentityColumn(this, it) }

    protected fun <U> U.identity(): LongDbIdentityColumnNotNull<T>
            where U : AbstractDbColumn<T, Long>,
                  U : LongColumnNullable<T> =
        LongDbIdentityColumnNotNull(this, Identity()).also { addIdentityColumn(this, it) }

    private fun addIdentityColumn(oldColumn: AbstractColumn<T, *>, identityColumn: AbstractColumn<T, *>) {
        // 1) remove previous column
        kotysaColumns.remove(oldColumn)
        // 1) add identity column
        addColumn(identityColumn)
    }
}
