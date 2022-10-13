/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.blocking

import org.ufoss.kotysa.SqlClient
import org.ufoss.kotysa.test.Customers

abstract class SelectGroupByRepository<T : Customers>(
    sqlClient: SqlClient,
    table: T,
) : AbstractCustomerRepository<T>(sqlClient, table) {

    fun selectCountCustomerGroupByCountry() =
        (sqlClient selectCount table.id and table.country
                from table
                groupBy table.country
                ).fetchAll()
}
