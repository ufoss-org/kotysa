/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mysql

import org.ufoss.kotysa.jdbc.JdbcSqlClient
import org.ufoss.kotysa.test.*

abstract class AbstractCustomerRepositoryJdbcMysql(private val sqlClient: JdbcSqlClient) : Repository {

    override fun init() {
        createTables()
        insertCustomers()
    }

    override fun delete() {
        deleteAll()
    }

    private fun createTables() {
        sqlClient createTableIfNotExists MYSQL_CUSTOMER
    }

    private fun insertCustomers() {
        sqlClient.insert(customerFrance, customerUSA1, customerUSA2)
    }

    private fun deleteAll() = sqlClient deleteAllFrom MYSQL_CUSTOMER
}
