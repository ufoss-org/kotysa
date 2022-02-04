/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mariadb

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.test.*


abstract class AbstractCustomerRepositorySpringJdbcMariadb(client: JdbcOperations) : Repository {

    protected val sqlClient = client.sqlClient(mariadbTables)

    override fun init() {
        createTables()
        insertCustomers()
    }

    override fun delete() {
        deleteAll()
    }

    private fun createTables() {
        sqlClient createTableIfNotExists MariadbCustomers
    }

    private fun insertCustomers() {
        sqlClient.insert(customerFrance, customerUSA1, customerUSA2)
    }

    private fun deleteAll() = sqlClient deleteAllFrom MariadbCustomers
}
