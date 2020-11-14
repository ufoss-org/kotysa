/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

/**
 * One database Table's Column model mapped by entity's [entityGetter]
 *
 * @param T Entity type associated with the table this column is in
 * @param U return type of associated getter to this column
 */
public interface KotysaColumn<T : Any, U : Any> {
    public val column: Column<T, U>
    /**
     * Table this column is in
     */
    public var table: KotysaTable<T>
    public val entityGetter: (T) -> Any?
    public val name: String
    public val sqlType: SqlType
    public val isAutoIncrement: Boolean
    public val isNullable: Boolean
    public val defaultValue: Any?
    public val size: Int ?
}

internal class KotysaColumnImpl<T : Any, U : Any> internal constructor(
        override val column: Column<T, U>,
        override val entityGetter: (T) -> Any?,
        override val name: String,
        override val sqlType: SqlType,
        override val isAutoIncrement: Boolean,
        override val isNullable: Boolean,
        override val defaultValue: Any?,
        override val size: Int ?,
) : KotysaColumn<T, U> {

    override lateinit var table: KotysaTable<T>

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as KotysaColumnImpl<*, *>

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}
