/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mariadb

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.test.*

abstract class AbstractCustomerRepositoryJdbcMariadb(private val sqlClient: JdbcSqlClient) : Repository {

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
