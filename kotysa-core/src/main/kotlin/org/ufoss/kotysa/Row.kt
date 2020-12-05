/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

public abstract class Row protected constructor(internal var offset: Int = 0) {
    internal final fun <T : Any> getWithOffset(index: Int, clazz: Class<T>) : T? =
            get(offset + index, clazz)

    public abstract fun <T : Any> get(index: Int, clazz: Class<T>) : T?

    public fun resetIndex() {
        offset = 0
    }
}