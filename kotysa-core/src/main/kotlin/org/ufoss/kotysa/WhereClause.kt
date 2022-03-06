/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

public enum class Operation {
    EQ, NOT_EQ, CONTAINS, STARTS_WITH, ENDS_WITH, SUP, INF, SUP_OR_EQ, INF_OR_EQ, IN, EXISTS
}

public sealed class WhereClause {
    internal abstract val operation: Operation
}

public sealed class WhereClauseWithColumn<T : Any> : WhereClause() {
    internal abstract val column: Column<T, *>
}

public class WhereClauseValue<T : Any> internal constructor(
    override val column: Column<T, *>,
    override val operation: Operation,
    public val value: Any?,
) : WhereClauseWithColumn<T>()

public class WhereClauseColumn<T : Any> internal constructor(
    override val column: Column<T, *>,
    override val operation: Operation,
    internal val otherColumn: Column<*, *>,
) : WhereClauseWithColumn<T>()

public class WhereClauseExists<T : Any> internal constructor(
    internal val dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>,
) : WhereClause() {
    override val operation: Operation = Operation.EXISTS
}

public class WhereClauseWithType internal constructor(
    public val whereClause: WhereClause,
    internal val type: WhereClauseType
)

public enum class WhereClauseType {
    WHERE, AND, OR
}
