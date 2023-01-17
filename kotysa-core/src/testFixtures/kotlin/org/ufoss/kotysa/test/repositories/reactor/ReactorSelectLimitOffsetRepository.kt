/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.reactor

import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.test.Customers

abstract class ReactorSelectLimitOffsetRepository<T : Customers>(
    sqlClient: ReactorSqlClient,
    table: T,
) : AbstractReactorCustomerRepository<T>(sqlClient, table) {

    fun selectAllOrderByIdOffset() =
        (sqlClient selectFrom table
                orderByAsc table.id
                offset 2
                ).fetchAll()

    fun selectAllOrderByIdLimit() =
        (sqlClient selectFrom table
                orderByAsc table.id
                limit 1
                ).fetchAll()

    fun selectAllLimitOffset() =
        (sqlClient selectFrom table
                limit 1 offset 1
                ).fetchAll()

    fun selectAllOrderByIdLimitOffset() =
        (sqlClient selectFrom table
                orderByAsc table.id
                limit 2 offset 1
                ).fetchAll()
}
