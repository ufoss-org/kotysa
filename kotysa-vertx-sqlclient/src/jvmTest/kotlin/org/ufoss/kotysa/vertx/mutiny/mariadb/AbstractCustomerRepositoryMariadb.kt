/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mariadb

import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.vertx.MutinySqlClient

abstract class AbstractCustomerRepositoryMariadb(protected val sqlClient: MutinySqlClient) : Repository {

    override fun init() {
        createTables()
            .chain { -> insertCustomers() }
            .await().indefinitely()
    }

    override fun delete() {
        deleteAll().await().indefinitely()
    }

    private fun createTables() = sqlClient createTableIfNotExists MariadbCustomers

    private fun insertCustomers() = sqlClient.insert(customerFrance, customerUSA1, customerUSA2)

    private fun deleteAll() = sqlClient deleteAllFrom MariadbCustomers
}
