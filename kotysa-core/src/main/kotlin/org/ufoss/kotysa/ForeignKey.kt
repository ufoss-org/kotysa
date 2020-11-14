/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa


public class ForeignKey<T : Any, U : Any> @PublishedApi internal constructor(
        internal val referencedTable: Table<U>,
        internal val columns: List<Column<T, *>>,
        internal val name: String?,
)
