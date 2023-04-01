/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.blocking

import org.ufoss.kotysa.SqlClient
import org.ufoss.kotysa.test.Customers

abstract class SelectOrderByRepository<T : Customers>(
    sqlClient: SqlClient,
    table: T,
) : AbstractCustomerRepository<T>(sqlClient, table) {

    fun selectCustomerOrderByAgeAsc() =
        (sqlClient selectFrom table
                orderByAsc table.age
                ).fetchAll()

    fun selectCustomerOrderByAgeAndIdAsc() =
        (sqlClient selectFrom table
                orderByAsc table.age andAsc table.id
                ).fetchAll()
}
