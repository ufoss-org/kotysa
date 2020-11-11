/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import org.ufoss.kotysa.columns.ColumnNotNull


public class PrimaryKey<T : Any> internal constructor(
        internal val name: String?,
        internal val columns: List<ColumnNotNull<T, *>>
)
