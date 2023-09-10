/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mssql

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MssqlCustomers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectOrderByRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectOrderByTest

class VertxCoroutinesSelectOrderByMssqlTest : AbstractVertxCoroutinesMssqlTest<OrderByRepositoryMssqlSelect>(),
    CoroutinesSelectOrderByTest<MssqlCustomers, OrderByRepositoryMssqlSelect, Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = OrderByRepositoryMssqlSelect(sqlClient)
}

class OrderByRepositoryMssqlSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectOrderByRepository<MssqlCustomers>(sqlClient, MssqlCustomers)
