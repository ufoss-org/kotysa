/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.reactor

import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.test.*

abstract class AbstractReactorCustomerRepository<T : Customers>(
    protected val sqlClient: ReactorSqlClient,
    protected val table: T,
) : Repository {

    override fun init() {
        createTables()
            .then(insertCustomers())
            .block()
    }

    override fun delete() {
        deleteAll()
            .block()
    }

    private fun createTables() = sqlClient createTableIfNotExists table

    private fun insertCustomers() = sqlClient.insert(customerFrance, customerUSA1, customerUSA2)

    private fun deleteAll() = sqlClient deleteAllFrom table
}
