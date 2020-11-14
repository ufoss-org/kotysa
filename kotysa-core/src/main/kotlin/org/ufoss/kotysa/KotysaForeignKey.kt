/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa


public class KotysaForeignKey<T : Any, U : Any> internal constructor(
        internal val referencedTable: Table<U>,
        internal val columns: List<KotysaColumn<T, *>>,
        internal val name: String?,
) {
    internal lateinit var referencedColumns: List<KotysaColumn<*, *>>
}
