/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.coroutines

import org.ufoss.kotysa.CoroutinesSqlClient
import org.ufoss.kotysa.test.Customers

abstract class CoroutinesSelectMinMaxAvgSumRepository<T : Customers>(
    sqlClient: CoroutinesSqlClient,
    table: T,
) : AbstractCoroutinesCustomerRepository<T>(sqlClient, table) {

    suspend fun selectCustomerMinAge() =
        (sqlClient selectMin table.age
                from table
                ).fetchOne()

    suspend fun selectCustomerMaxAge() =
        (sqlClient selectMax table.age
                from table
                ).fetchOne()

    suspend fun selectCustomerAvgAge() =
        (sqlClient selectAvg table.age
                from table
                ).fetchOne()

    suspend fun selectCustomerSumAge() =
        (sqlClient selectSum table.age
                from table
                ).fetchOne()
}
