/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import kotlin.reflect.KClass

public class ForeignKeyBuilder<T : Any, U : Any>(
        private val referencedClass: KClass<U>,
        private vararg val properties: ColumnProperty<T>
) {

    private var name: String? = null
    private var referencedProperties = mutableSetOf<ColumnProperty<U>>()

    public fun name(foreignKeyName: String): ForeignKeyBuilder<T, U> {
        name = foreignKeyName
        return this
    }

    public fun references(vararg referencedColumnProperties: ColumnProperty<U>): ForeignKeyBuilder<T, U> {
        // in case
        referencedProperties.clear()
        referencedProperties.addAll(referencedColumnProperties)
        return this
    }

    internal fun build() = ForeignKey(properties.toHashSet(), referencedProperties, referencedClass, name)
}
