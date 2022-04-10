/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

public sealed interface FromClause<T : Any>

public class FromClauseTable<T : Any> internal constructor(
    internal val table: Table<T>
) : FromClause<T> {
    internal val joinClauses = mutableListOf<JoinClause<T, *>>()
}

public class FromClauseSubQuery<T : Any> internal constructor(
    internal val result: SqlClientSubQuery.Return<T>
) : FromClause<T>
