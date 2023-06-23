/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.reactor

import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.test.Customers

abstract class ReactorSelectMinMaxAvgSumRepository<T : Customers>(
    sqlClient: ReactorSqlClient,
    table: T,
) : AbstractReactorCustomerRepository<T>(sqlClient, table) {

    fun selectCustomerMinAge() =
        (sqlClient selectMin table.age
                from table
                ).fetchOne()

    fun selectCustomerMaxAge() =
        (sqlClient selectMax table.age
                from table
                ).fetchOne()

    fun selectCustomerAvgAge() =
        (sqlClient selectAvg table.age
                from table
                ).fetchOne()

    fun selectCustomerSumAge() =
        (sqlClient selectSum table.age
                from table
                ).fetchOne()

    fun selectCustomerSumId() =
        (sqlClient selectSum table.id
                from table
                ).fetchOne()
}
