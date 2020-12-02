/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

public abstract class Row protected constructor(internal var index: Int = 0) {
    protected abstract operator fun <T : Any> get(index: Int, clazz: Class<T>) : T?

    internal operator fun <T : Any> get(clazz: Class<T>) : T? = get(index++, clazz)

    public fun resetIndex() {
        index = 0
    }
}