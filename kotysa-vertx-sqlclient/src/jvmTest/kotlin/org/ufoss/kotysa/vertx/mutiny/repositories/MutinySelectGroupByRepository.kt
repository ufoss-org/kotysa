/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.repositories

import org.ufoss.kotysa.vertx.MutinySqlClient
import org.ufoss.kotysa.test.Customers

abstract class MutinySelectGroupByRepository<T : Customers>(
    sqlClient: MutinySqlClient,
    table: T,
) : AbstractMutinyCustomerRepository<T>(sqlClient, table) {

    fun selectCountCustomerGroupByCountry() =
        (sqlClient selectCount table.id and table.country
                from table
                groupBy table.country
                ).fetchAll()
}
