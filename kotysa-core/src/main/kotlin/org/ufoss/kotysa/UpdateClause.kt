/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

public sealed interface UpdateClause<T : Any> {
    public val column: Column<T, *>
}

public class UpdateClauseValue<T : Any> internal constructor(
    override val column: Column<T, *>,
) : UpdateClause<T>

internal class UpdateClauseColumn<T : Any> internal constructor(
    override val column: Column<T, *>,
    internal val otherColumn: Column<*, *>
) : UpdateClause<T> {
    internal var increment: Int? = null
}
