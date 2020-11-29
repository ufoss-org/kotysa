/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

public interface Row {
    public operator fun <T : Any> get(index: Int, clazz: Class<T>) : T?
}