/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.coroutines

import org.ufoss.kotysa.CoroutinesSqlClient
import org.ufoss.kotysa.test.Customers

abstract class CoroutinesSelectOrderByRepository<T : Customers>(
    sqlClient: CoroutinesSqlClient,
    table: T,
) : AbstractCoroutinesCustomerRepository<T>(sqlClient, table) {

    fun selectCustomerOrderByAgeAsc() =
        (sqlClient selectFrom table
                orderByAsc table.age
                ).fetchAll()

    fun selectCustomerOrderByAgeAndIdAsc() =
        (sqlClient selectFrom table
                orderByAsc table.age andAsc table.id
                ).fetchAll()
}
