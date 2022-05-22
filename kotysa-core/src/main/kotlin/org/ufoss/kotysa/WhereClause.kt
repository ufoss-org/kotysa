/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

public enum class Operation {
    EQ, NOT_EQ, CONTAINS, STARTS_WITH, ENDS_WITH, SUP, INF, SUP_OR_EQ, INF_OR_EQ, IN, EXISTS
}

public sealed interface WhereClause {
    public val operation: Operation
}

public sealed interface WhereClauseWithColumn<T : Any> : WhereClause {
    public val column: Column<T, *>
}

public sealed interface WhereClauseWithAlias<T> : WhereClause {
    public val alias: QueryAlias<T>
}

public sealed interface WhereClauseValue {
    public val value: Any?
}

public sealed interface WhereClauseColumn {
    public val otherColumn: Column<*, *>
}

public sealed interface WhereClauseSubQuery<T : Any> {
    public val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
}

public class WhereClauseValueWithColumn<T : Any> internal constructor(
    override val column: Column<T, *>,
    override val operation: Operation,
    override val value: Any?,
) : WhereClauseWithColumn<T>, WhereClauseValue

public class WhereClauseValueWithAlias<T> internal constructor(
    override val alias: QueryAlias<T>,
    override val operation: Operation,
    override val value: Any?,
) : WhereClauseWithAlias<T>, WhereClauseValue

public class WhereClauseColumnWithColumn<T : Any> internal constructor(
    override val column: Column<T, *>,
    override val operation: Operation,
    override val otherColumn: Column<*, *>,
) : WhereClauseWithColumn<T>, WhereClauseColumn

public class WhereClauseColumnWithAlias<T> internal constructor(
    override val alias: QueryAlias<T>,
    override val operation: Operation,
    override val otherColumn: Column<*, *>,
) : WhereClauseWithAlias<T>, WhereClauseColumn

public class WhereClauseExists<T : Any> internal constructor(
    internal val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>,
) : WhereClause {
    override val operation: Operation = Operation.EXISTS
}

public class WhereClauseSubQueryWithColumn<T : Any, U : Any> internal constructor(
    override val column: Column<T, U>,
    override val operation: Operation,
    override val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>,
) : WhereClauseWithColumn<T>, WhereClauseSubQuery<U>

public class WhereClauseSubQueryWithAlias<T, U : Any> internal constructor(
    override val alias: QueryAlias<T>,
    override val operation: Operation,
    override val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<U>,
) : WhereClauseWithAlias<T>, WhereClauseSubQuery<U>

public class WhereClauseWithType internal constructor(
    public val whereClause: WhereClause,
    internal val type: WhereClauseType
)

public enum class WhereClauseType {
    WHERE, AND, OR
}
