/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa


public class JoinClause<T : Any, U : Any> internal constructor(
        internal val references: Map<DbColumn<T, *>, DbColumn<U, *>>,
        internal val type: JoinType
)


public enum class JoinType(internal val sql: String) {
    INNER("INNER JOIN"),
    LEFT_OUTER("LEFT OUTER JOIN"),
    RIGHT_OUTER("RIGHT OUTER JOIN"),
    FULL_OUTER("OUTER JOIN"),
    CROSS("CROSSS JOIN")
}
