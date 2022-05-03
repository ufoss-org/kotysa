/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa


public class JoinClause<T : Any, U : Any> internal constructor(
        internal val table: Table<U>,
        internal val references: Map<Column<T, *>, Column<U, *>>,
        internal val type: JoinClauseType,
        internal val alias: String?,
)


public enum class JoinClauseType(internal val sql: String) {
    INNER("INNER JOIN"),
    LEFT_OUTER("LEFT OUTER JOIN"),
    RIGHT_OUTER("RIGHT OUTER JOIN"),
    FULL_OUTER("OUTER JOIN"),
    CROSS("CROSSS JOIN")
}
