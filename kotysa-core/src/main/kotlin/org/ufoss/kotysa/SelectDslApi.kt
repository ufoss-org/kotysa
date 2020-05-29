/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import kotlin.reflect.KClass


public abstract class SelectDslApi protected constructor(){
    @PublishedApi
    internal abstract fun <T : Any> count(resultClass: KClass<T>, dsl: ((FieldProvider) -> ColumnField<T, *>)? = null,
                                          alias: String? = null): Long
}


public inline fun <reified T : Any> SelectDslApi.count(
        noinline dsl: ((FieldProvider) -> ColumnField<T, *>
        )? = null): Long = count(T::class, dsl)
