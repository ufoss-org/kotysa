/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import kotlin.reflect.KCallable
import kotlin.reflect.KFunction
import kotlin.reflect.KProperty1


public fun <T : Any> ((T) -> Any?).toCallable(): KCallable<Any?> =
        when (this) {
            is KProperty1<T, *> -> this
            is KFunction<*> -> this
            else -> throw RuntimeException("Wrong type for $this, support only KProperty1 and KFunction")
        }
