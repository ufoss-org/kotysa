package org.ufoss.kotysa

public class FromClause<T : Any> internal constructor(
    internal val table: Table<T>
) {
    internal val joinClauses = mutableListOf<JoinClause<T, *>>()
}