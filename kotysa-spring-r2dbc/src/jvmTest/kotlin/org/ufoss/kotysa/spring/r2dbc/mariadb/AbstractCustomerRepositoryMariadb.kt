/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mariadb

import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.test.*


abstract class AbstractCustomerRepositoryMariadb(protected val sqlClient: ReactorSqlClient) : Repository {

    override fun init() {
        createTables()
                .then(insertCustomers().then())
                .block()
    }

    override fun delete() {
        deleteAll().block()
    }

    private fun createTables() = sqlClient createTableIfNotExists MariadbCustomers

    private fun insertCustomers() = sqlClient.insert(customerFrance, customerUSA1, customerUSA2)

    private fun deleteAll() = sqlClient deleteAllFrom MariadbCustomers
}
