/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import kotlin.reflect.KClass


public class ForeignKey<T : Any, U : Any> internal constructor(
	internal val properties: Set<ColumnProperty<T>>,
	internal val referencedProperties: Set<ColumnProperty<U>>,
	internal val referencedClass: KClass<U>,
	internal val name: String?
) {
	internal lateinit var referencedTable: Table<*>
	internal lateinit var referencedColumns: List<Column<*, *>>
	internal lateinit var columns: List<Column<*, *>>
}
