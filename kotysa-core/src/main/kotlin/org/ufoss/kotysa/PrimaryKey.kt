/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa


public class PrimaryKey<T : Any> internal constructor(
        internal val name: String?,
        internal val columns: List<ColumnNotNull<T, *>>,
)
