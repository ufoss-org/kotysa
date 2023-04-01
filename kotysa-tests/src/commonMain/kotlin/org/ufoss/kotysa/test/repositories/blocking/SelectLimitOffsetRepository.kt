/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.blocking

import org.ufoss.kotysa.SqlClient
import org.ufoss.kotysa.test.Customers

abstract class SelectLimitOffsetRepository<T : Customers>(
    sqlClient: SqlClient,
    table: T,
) : AbstractCustomerRepository<T>(sqlClient, table) {

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
