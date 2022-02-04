/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.h2

import org.ufoss.kotysa.spring.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.test.*


abstract class AbstractCustomerRepositoryH2(
    protected val sqlClient: ReactorSqlClient,
) : Repository {

    override fun init() {
        createTables()
                .then(insertCustomers().then())
                .block()
    }

    override fun delete() {
        deleteAll().block()
    }

    private fun createTables() = sqlClient createTableIfNotExists H2Customers

    private fun insertCustomers() = sqlClient.insert(customerFrance, customerUSA1, customerUSA2)

    private fun deleteAll() = sqlClient deleteAllFrom H2Customers
}
