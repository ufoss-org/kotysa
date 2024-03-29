/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.blocking

import org.ufoss.kotysa.SqlClient
import org.ufoss.kotysa.test.Customers

abstract class SelectMinMaxAvgSumRepositoryCommon<T : Customers>(
    sqlClient: SqlClient,
    table: T,
) : AbstractCustomerRepository<T>(sqlClient, table) {

    fun selectCustomerMinAge() =
        (sqlClient selectMin table.age
                from table
                ).fetchOne()

    fun selectCustomerMaxAge() =
        (sqlClient selectMax table.age
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

expect abstract class SelectMinMaxAvgSumRepository<T : Customers>(
    sqlClient: SqlClient,
    table: T,
) : SelectMinMaxAvgSumRepositoryCommon<T>
