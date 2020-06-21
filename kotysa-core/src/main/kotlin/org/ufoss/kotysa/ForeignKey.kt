/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import kotlin.reflect.KClass


public class ForeignKey<T : Any, U : Any> @PublishedApi internal constructor(
        internal val referencedClass: KClass<U>,
        internal val columns: List<Column<T, *>>,
        internal val name: String?
) {
    internal lateinit var referencedTable: Table<*>
    internal lateinit var referencedColumns: List<Column<*, *>>
}
