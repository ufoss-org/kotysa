/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import io.r2dbc.spi.Connection
import kotlinx.coroutines.runBlocking
import org.ufoss.kotysa.r2dbc.sqlClient
import org.ufoss.kotysa.test.*

abstract class AbstractCustomerRepositoryR2dbcPostgresql(connection: Connection) : Repository {

    protected val sqlClient = connection.sqlClient(postgresqlTables)

    override fun init() = runBlocking {
        createTables()
        insertCustomers()
    }

    override fun delete() = runBlocking<Unit> {
        deleteAll()
    }

    private suspend fun createTables() {
        sqlClient createTableIfNotExists POSTGRESQL_CUSTOMER
    }

    private suspend fun insertCustomers() {
        sqlClient.insert(customerFrance, customerUSA1, customerUSA2)
    }

    private suspend fun deleteAll() = sqlClient deleteAllFrom POSTGRESQL_CUSTOMER
}
