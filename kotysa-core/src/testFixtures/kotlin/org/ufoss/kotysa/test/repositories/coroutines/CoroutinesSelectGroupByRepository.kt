/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.coroutines

import org.ufoss.kotysa.CoroutinesSqlClient
import org.ufoss.kotysa.test.Customers

abstract class CoroutinesSelectGroupByRepository<T : Customers>(
    sqlClient: CoroutinesSqlClient,
    table: T,
) : AbstractCoroutinesCustomerRepository<T>(sqlClient, table) {

    fun selectCountCustomerGroupByCountry() =
        (sqlClient selectCount table.id and table.country
                from table
                groupBy table.country
                ).fetchAll()
}
