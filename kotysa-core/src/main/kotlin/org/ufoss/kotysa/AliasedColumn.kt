/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

/**
 * A Column with an alias
 */
internal class AliasedColumn<out T : Any, U : Any> internal constructor(
        column: Column<T, U>,
        internal val alias: String
) : Column<T, U> by column
