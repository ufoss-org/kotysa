/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import org.ufoss.kotysa.jdbc.JdbcSqlClient
import org.ufoss.kotysa.test.*

abstract class AbstractCustomerRepositoryJdbcH2(private val sqlClient: JdbcSqlClient) : Repository {

    override fun init() {
        createTables()
        insertCustomers()
    }

    override fun delete() {
        deleteAll()
    }

    private fun createTables() {
        sqlClient createTableIfNotExists H2_CUSTOMER
    }

    private fun insertCustomers() {
        sqlClient.insert(customerFrance, customerUSA1, customerUSA2)
    }

    private fun deleteAll() = sqlClient deleteAllFrom H2_CUSTOMER
}
