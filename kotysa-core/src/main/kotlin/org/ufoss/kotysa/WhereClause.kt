/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa


public class WhereClause internal constructor(
        internal val field: Field,
        internal val operation: Operation,
        public val value: Any?
)

internal enum class Operation {
    EQ, NOT_EQ, CONTAINS, STARTS_WITH, ENDS_WITH, SUP, INF, SUP_OR_EQ, INF_OR_EQ, IS, IN
}

public class TypedWhereClause internal constructor(
        public val whereClause: WhereClause,
        internal val type: WhereClauseType
)

internal enum class WhereClauseType {
    WHERE, AND, OR
}
