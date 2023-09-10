/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.repositories

import org.ufoss.kotysa.vertx.MutinySqlClient
import org.ufoss.kotysa.test.Customers

abstract class MutinySelectMinMaxAvgSumRepository<T : Customers>(
    sqlClient: MutinySqlClient,
    table: T,
) : AbstractMutinyCustomerRepository<T>(sqlClient, table) {

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
