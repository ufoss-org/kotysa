/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import org.ufoss.kotysa.columns.AbstractDbColumn

public class PrimaryKey<T : Any> internal constructor(
    internal val name: String?,
    public val columns: Set<AbstractDbColumn<T, *>>,
)
