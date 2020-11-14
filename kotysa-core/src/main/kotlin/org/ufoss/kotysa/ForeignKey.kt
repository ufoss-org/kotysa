/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import org.ufoss.kotysa.columns.KotysaColumn


public class ForeignKey<T : Any, U : Any> @PublishedApi internal constructor(
        internal val referencedTable: Table<U>,
        internal val columns: List<KotysaColumn<T, *>>,
        internal val name: String?
) {
    internal lateinit var referencedKotysaTable: KotysaTable<*>
    internal lateinit var referencedColumns: List<KotysaColumn<*, *>>
}
