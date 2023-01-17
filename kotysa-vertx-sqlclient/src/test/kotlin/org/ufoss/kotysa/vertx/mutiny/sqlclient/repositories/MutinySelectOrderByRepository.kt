/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories

import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinySqlClient
import org.ufoss.kotysa.test.Customers

abstract class MutinySelectOrderByRepository<T : Customers>(
    sqlClient: MutinySqlClient,
    table: T,
) : AbstractMutinyCustomerRepository<T>(sqlClient, table) {

    fun selectCustomerOrderByAgeAsc() =
        (sqlClient selectFrom table
                orderByAsc table.age
                ).fetchAll()

    fun selectCustomerOrderByAgeAndIdAsc() =
        (sqlClient selectFrom table
                orderByAsc table.age andAsc table.id
                ).fetchAll()
}
