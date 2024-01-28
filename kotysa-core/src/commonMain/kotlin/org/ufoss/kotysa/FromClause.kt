/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import org.ufoss.kotysa.postgresql.Tsquery

public sealed interface FromClause {
    public var alias: String?
}

internal sealed class AbstractFromClause<T : Any> : FromClause {
    final override var alias: String? = null
}

internal class FromClauseTable<T : Any> internal constructor(
    internal val table: Table<T>
) : AbstractFromClause<T>() {
    internal val joinClauses = mutableListOf<JoinClause<*, *>>()
}

internal class FromClauseSubQuery<T : Any> internal constructor(
    internal val result: SqlClientSubQuery.Return<T>,
    internal val selectStar: Boolean,
) : AbstractFromClause<T>()

internal class FromClauseTsquery internal constructor(
    internal val tsquery: Tsquery
) : FromClause {
    override var alias: String? = tsquery.alias
}
