/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

public enum class Order {
    ASC, DESC
}

public sealed class OrderByClause {
    internal abstract val order: Order
}

internal class OrderByClauseWithColumn internal constructor(
    internal val column: Column<*, *>,
    override val order: Order,
) : OrderByClause()

public class OrderByClauseWithAlias internal constructor(
    internal val alias: QueryAlias<*>,
    override val order: Order,
) : OrderByClause()

internal class OrderByClauseCaseWhenExistsSubQuery<T : Any> internal constructor(
    internal val subQueryReturn: SqlClientSubQuery.Return<*>,
    internal val then: T,
    internal val elseVal: T,
    override val order: Order,
) : OrderByClause()


