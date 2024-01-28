/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mysql

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MysqlCustomers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectOrderByRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectOrderByTest

class VertxCoroutinesSelectOrderByMysqlTest : AbstractVertxCoroutinesMysqlTest<OrderByRepositoryMysqlSelect>(),
    CoroutinesSelectOrderByTest<MysqlCustomers, OrderByRepositoryMysqlSelect, Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = OrderByRepositoryMysqlSelect(sqlClient)
}

class OrderByRepositoryMysqlSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectOrderByRepository<MysqlCustomers>(sqlClient, MysqlCustomers)
