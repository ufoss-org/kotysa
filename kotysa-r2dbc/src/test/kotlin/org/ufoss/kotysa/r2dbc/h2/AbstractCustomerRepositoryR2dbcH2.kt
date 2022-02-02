/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import kotlinx.coroutines.runBlocking
import org.ufoss.kotysa.r2dbc.R2dbcSqlClient
import org.ufoss.kotysa.test.*

abstract class AbstractCustomerRepositoryR2dbcH2(private val sqlClient: R2dbcSqlClient) : Repository {

    override fun init() = runBlocking {
        createTables()
        insertCustomers()
    }

    override fun delete() = runBlocking<Unit> {
        deleteAll()
    }

    private suspend fun createTables() {
        sqlClient createTableIfNotExists H2_CUSTOMER
    }

    private suspend fun insertCustomers() {
        sqlClient.insert(customerFrance, customerUSA1, customerUSA2)
    }

    private suspend fun deleteAll() = sqlClient deleteAllFrom H2_CUSTOMER
}
