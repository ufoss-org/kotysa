/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import org.ufoss.kotysa.postgresql.Tsquery

public enum class Operation {
    EQ, NOT_EQ, CONTAINS, STARTS_WITH, ENDS_WITH, SUP, INF, SUP_OR_EQ, INF_OR_EQ, IN, EXISTS,
    // Postgresql specific tsquery
    TO_TSQUERY, PLAINTO_TSQUERY, PHRASETO_TSQUERY, WEBSEARCH_TO_TSQUERY, APPLY_ON
}

internal sealed interface WhereClause {
    val operation: Operation
}

internal interface WhereClauseWithColumn<T : Any> : WhereClause {
    val column: Column<T, *>
}

internal sealed interface WhereClauseWithAlias<T> : WhereClause {
    val alias: QueryAlias<T>
}

internal sealed interface WhereClauseValue {
    val value: Any?
}

internal sealed interface WhereClauseColumn {
    val otherColumn: Column<*, *>
}

internal sealed interface WhereClauseSubQuery<T : Any> {
    val result: SubQueryResult<T>
}

internal class WhereClauseValueWithColumn<T : Any> internal constructor(
    override val column: Column<T, *>,
    override val operation: Operation,
    override val value: Any?,
) : WhereClauseWithColumn<T>, WhereClauseValue

internal class WhereClauseValueWithAlias<T> internal constructor(
    override val alias: QueryAlias<T>,
    override val operation: Operation,
    override val value: Any?,
) : WhereClauseWithAlias<T>, WhereClauseValue

internal class WhereClauseColumnWithColumn<T : Any> internal constructor(
    override val column: Column<T, *>,
    override val operation: Operation,
    override val otherColumn: Column<*, *>,
) : WhereClauseWithColumn<T>, WhereClauseColumn

internal class WhereClauseColumnWithAlias<T> internal constructor(
    override val alias: QueryAlias<T>,
    override val operation: Operation,
    override val otherColumn: Column<*, *>,
) : WhereClauseWithAlias<T>, WhereClauseColumn

internal class WhereClauseExists<T : Any> internal constructor(
    internal val dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>,
) : WhereClause {
    override val operation: Operation = Operation.EXISTS
}

internal class WhereClauseSubQueryWithColumn<T : Any, U : Any> internal constructor(
    override val column: Column<T, U>,
    override val operation: Operation,
    override val result: SubQueryResult<U>,
) : WhereClauseWithColumn<T>, WhereClauseSubQuery<U>

internal class WhereClauseSubQueryWithAlias<T, U : Any> internal constructor(
    override val alias: QueryAlias<T>,
    override val operation: Operation,
    override val result: SubQueryResult<U>,
) : WhereClauseWithAlias<T>, WhereClauseSubQuery<U>

public class WhereClauseWithType internal constructor(
    internal val whereClause: WhereClause,
    internal val type: WhereClauseType
)

public enum class WhereClauseType {
    WHERE, OR
}

internal class WhereClauseTsqueryWithColumn<T : Any> internal constructor(
    override val column: Column<T, *>,
    internal val tsquery: Tsquery,
) : WhereClauseWithColumn<T> {
    override val operation = Operation.APPLY_ON
}
