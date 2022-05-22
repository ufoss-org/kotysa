/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

public sealed interface FromClause<T : Any> {
    public var alias: String?
}

internal sealed class AbstractFromClause<T : Any> : FromClause<T> {
    final override var alias: String? = null
}

internal class FromClauseTable<T : Any> internal constructor(
    internal val table: Table<T>
) : AbstractFromClause<T>() {
    internal val joinClauses = mutableListOf<JoinClause<T, *>>()
}

internal class FromClauseSubQuery<T : Any> internal constructor(
    internal val result: SqlClientSubQuery.Return<T>,
    internal val selectStar: Boolean,
) : AbstractFromClause<T>()
