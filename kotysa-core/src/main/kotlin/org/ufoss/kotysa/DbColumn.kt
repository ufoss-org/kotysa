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
    internal abstract val entityGetter: (T) -> U?
    internal abstract val name: String?
    internal abstract val sqlType: SqlType
    internal abstract val isAutoIncrement: Boolean
    internal abstract val size: Int?
    internal abstract val isNullable: Boolean
    internal abstract val defaultValue: U?

    override fun toString(): String {
        return "DbColumn(name='$name', entityGetter=$entityGetter)"
    }
}
