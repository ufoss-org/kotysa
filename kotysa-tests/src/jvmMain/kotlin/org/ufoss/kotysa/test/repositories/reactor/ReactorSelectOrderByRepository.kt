/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.reactor

import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.test.Customers

abstract class ReactorSelectOrderByRepository<T : Customers>(
    sqlClient: ReactorSqlClient,
    table: T,
) : AbstractReactorCustomerRepository<T>(sqlClient, table) {

    fun selectCustomerOrderByAgeAsc() =
        (sqlClient selectFrom table
                orderByAsc table.age
                ).fetchAll()

    fun selectCustomerOrderByAgeAndIdAsc() =
        (sqlClient selectFrom table
                orderByAsc table.age andAsc table.id
                ).fetchAll()
}
