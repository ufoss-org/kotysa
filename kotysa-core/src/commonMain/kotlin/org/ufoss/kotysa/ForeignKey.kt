/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import org.ufoss.kotysa.columns.AbstractDbColumn


public data class ForeignKey<T : Any, U : Any> internal constructor(
    internal val references: Map<AbstractDbColumn<T, *>, AbstractDbColumn<U, *>>,
    internal val name: String?,
)
