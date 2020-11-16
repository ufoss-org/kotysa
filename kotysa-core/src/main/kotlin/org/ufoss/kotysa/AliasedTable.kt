/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

/**
 * A table with an alias (that may be null)
 */
internal class AliasedTable<T : Any> internal constructor(
        table: Table<T>,
        internal val alias: String
) : Table<T>(table.name) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AliasedTable<*>

        if (alias != other.alias) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + alias.hashCode()
        return result
    }
}
