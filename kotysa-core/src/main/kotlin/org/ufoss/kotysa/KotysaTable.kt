/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import kotlin.reflect.KClass

/**
 * A database Table model mapped by entity class [tableClass]
 */
public interface KotysaTable<T : Any> {
    public val tableClass: KClass<T>
    public val table: Table<T>

    /**
     * Real name of this table in the database
     */
    public val name: String
    public val columns: List<KotysaColumn<T, *>>
    public val primaryKey: PrimaryKey<T>
    public val foreignKeys: Set<ForeignKey<T, *>>
}


internal class KotysaTableImpl<T : Any> internal constructor(
        override val tableClass: KClass<T>,
        override val table: Table<T>,
        override val name: String,
        override val columns: List<KotysaColumn<T, *>>,
        override val primaryKey: PrimaryKey<T>,
        override val foreignKeys: Set<ForeignKey<T, *>>,
) : KotysaTable<T> {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as KotysaTableImpl<*>

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}
