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
    /**
     * Table this column is in
     */
    public val table: KotysaTable<T>
    public val entityGetter: (T) -> U?
    public val name: String
    public val sqlType: SqlType
    public val isAutoIncrement: Boolean
    public val isNullable: Boolean
    public val defaultValue: U?
    public val size: Int ?
}

internal class KotysaColumnImpl<T : Any, U : Any> internal constructor(
        override val table: KotysaTable<T>,
        override val entityGetter: (T) -> U?,
        override val name: String,
        override val sqlType: SqlType,
        override val isAutoIncrement: Boolean,
        override val isNullable: Boolean,
        override val defaultValue: U?,
        override val size: Int ?,
) : KotysaColumn<T, U> {

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
