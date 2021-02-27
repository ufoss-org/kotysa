/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.toCallable

public abstract class ColumnDescriptionDsl<T : Any, U : Any> protected constructor(private val entityGetter: (T) -> U?) {
    public var name: String? = null

    internal val columnName get() = name ?: entityGetter.toCallable().name
}