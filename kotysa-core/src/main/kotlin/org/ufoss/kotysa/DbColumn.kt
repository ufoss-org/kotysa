/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

/**
 * Represents a Column
 *
 * @param T Entity type associated with the table this column is in
 * @param U return type of associated getter to this column
 */
public abstract class DbColumn<T : Any, U : Any> internal constructor() : Column<T, U> {
    public abstract val entityGetter: (T) -> U?
    internal abstract val columnName: String?
    internal abstract val sqlType: SqlType
    internal abstract val isAutoIncrement: Boolean
    internal abstract val size: Int?
    internal abstract val isNullable: Boolean
    internal abstract val defaultValue: U?
    internal lateinit var name: String

    override fun toString(): String {
        return "DbColumn(entityGetter=$entityGetter)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DbColumn<*, *>

        if (entityGetter != other.entityGetter) return false

        return true
    }

    override fun hashCode(): Int {
        return entityGetter.hashCode()
    }


}
