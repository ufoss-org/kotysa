/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import org.ufoss.kotysa.columns.AbstractColumn


public data class Index<T : Any> internal constructor(
    internal val columns: Set<AbstractColumn<T, *>>,
    internal val type: IndexType?,
    internal val name: String?,
)
