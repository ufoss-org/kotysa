/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa


public class WhereClause<T : Any> internal constructor(
        internal val column: Column<T, *>,
        internal val operation: Operation,
        public val value: Any?
)

public enum class Operation {
    EQ, NOT_EQ, CONTAINS, STARTS_WITH, ENDS_WITH, SUP, INF, SUP_OR_EQ, INF_OR_EQ, IS, IN
}

public class TypedWhereClause<T : Any> internal constructor(
        public val whereClause: WhereClause<T>,
        internal val type: WhereClauseType
)

public enum class WhereClauseType {
    WHERE, AND, OR
}
