/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa


public data class Index<T : Any> internal constructor(
    internal val columns: Set<DbColumn<T, *>>,
    internal val type: IndexType?,
    internal val name: String?,
)
