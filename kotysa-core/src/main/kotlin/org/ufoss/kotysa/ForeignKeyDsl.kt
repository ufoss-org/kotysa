/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import kotlin.reflect.KClass

@KotysaMarker
public class ForeignKeyDsl<T : Any, U : Any> @PublishedApi internal constructor(
        private val init: ForeignKeyDsl<T, U>.(TableColumnPropertyProvider) -> ForeignKeyBuilder<T, U>,
        private val referencedClass: KClass<U>
) : AbstractTableColumnPropertyProvider() {

    public fun column(columnProperty: ColumnProperty<T>) : ForeignKeyBuilder<T, U> =
        ForeignKeyBuilder(referencedClass, columnProperty)

    public fun columns(vararg columnProperties: ColumnProperty<T>) : ForeignKeyBuilder<T, U> =
            ForeignKeyBuilder(referencedClass, *columnProperties)

    @PublishedApi
    internal fun initialize(): ForeignKey<T, U> {
        return init(this).build()
    }
}
