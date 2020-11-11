/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa


public class JoinClause internal constructor(
        internal val table: AliasedKotysaTable<*>,
        internal val field: ColumnField<*, *>,
        internal val type: JoinType
)


public enum class JoinType(internal val sql: String) {
    INNER("INNER JOIN"),
    LEFT_OUTER("LEFT OUTER JOIN"),
    RIGHT_OUTER("RIGHT OUTER JOIN"),
    FULL_OUTER("OUTER JOIN"),
    CROSS("CROSSS JOIN")
}
