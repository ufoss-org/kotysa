/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa


public interface ForeignKey {
	public val name: String?
}


internal data class SingleForeignKey<T : Any, U> internal constructor(
        override val name: String?,
        internal val column: Column<T, U>,
        internal var referencedColumn: Column<*, *>
) : ForeignKey
