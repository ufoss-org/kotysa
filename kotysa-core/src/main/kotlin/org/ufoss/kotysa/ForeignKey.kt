/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa


public class ForeignKey<T : Any, U : Any> internal constructor(
        internal val references: Map<DbColumn<T, *>, DbColumn<U, *>>,
        internal val name: String?,
)
