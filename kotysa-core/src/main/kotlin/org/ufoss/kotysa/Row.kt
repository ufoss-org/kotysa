/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

public abstract class Row protected constructor() {
    private var index = 0
    private var delayedIndex = 0
    internal fun <T : Any> getWithOffset(offset: Int, clazz: Class<T>): T? {
        delayedIndex++
        return get(this.index + offset, clazz)
    }
    internal fun <T : Any> getAndIncrement(clazz: Class<T>) : T? =
            get(this.index++, clazz)
    internal fun incrementWithDelayedIndex() {
        this.index += this.delayedIndex
        this.delayedIndex = 0
    }


    protected abstract fun <T : Any> get(index: Int, clazz: Class<T>) : T?



    public fun resetIndex() {
        index = 0
    }
}