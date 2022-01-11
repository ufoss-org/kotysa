/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

public enum class Operation {
    EQ, NOT_EQ, CONTAINS, STARTS_WITH, ENDS_WITH, SUP, INF, SUP_OR_EQ, INF_OR_EQ, IN
}

public sealed class WhereClause<T : Any> {
    internal abstract val column: Column<T, *>
    internal abstract val operation: Operation
}

public class WhereClauseValue<T : Any> internal constructor(
    override val column: Column<T, *>,
    override val operation: Operation,
    public val value: Any?,
) : WhereClause<T>()

public class WhereClauseColumn<T : Any> internal constructor(
    override val column: Column<T, *>,
    override val operation: Operation,
    internal val otherColumn: Column<*, *>,
) : WhereClause<T>()

public class WhereClauseWithType<T : Any> internal constructor(
    public val whereClause: WhereClause<T>,
    internal val type: WhereClauseType
)

public enum class WhereClauseType {
    WHERE, AND, OR
}
