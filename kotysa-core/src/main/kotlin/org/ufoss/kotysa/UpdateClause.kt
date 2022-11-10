/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

public sealed interface UpdateClause<T : Any> {
    public val column: DbColumn<T, *>
}

public class UpdateClauseValue<T : Any> internal constructor(
    override val column: DbColumn<T, *>,
) : UpdateClause<T>

internal class UpdateClauseColumn<T : Any> internal constructor(
    override val column: DbColumn<T, *>,
    internal val otherColumn: Column<*, *>
) : UpdateClause<T> {
    internal var increment: Int? = null
}
