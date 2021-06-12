/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mariadb

import org.ufoss.kotysa.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.test.*


abstract class AbstractCustomerRepositoryMariadb(protected val sqlClient: ReactorSqlClient) : Repository {

    override fun init() {
        createTables()
                .then(insertCustomers())
                .block()
    }

    override fun delete() {
        deleteAll().block()
    }

    private fun createTables() = sqlClient createTableIfNotExists MARIADB_CUSTOMER

    private fun insertCustomers() = sqlClient.insert(customerFrance, customerUSA1, customerUSA2)

    private fun deleteAll() = sqlClient deleteAllFrom MARIADB_CUSTOMER
}
