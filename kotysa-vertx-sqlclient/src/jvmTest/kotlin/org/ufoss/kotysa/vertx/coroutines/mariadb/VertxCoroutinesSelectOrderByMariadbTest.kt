/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mariadb

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MariadbCustomers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectOrderByRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectOrderByTest

class VertxCoroutinesSelectOrderByMariadbTest : AbstractVertxCoroutinesMariadbTest<OrderByRepositoryMariadbSelect>(),
    CoroutinesSelectOrderByTest<MariadbCustomers, OrderByRepositoryMariadbSelect, Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = OrderByRepositoryMariadbSelect(sqlClient)
}

class OrderByRepositoryMariadbSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectOrderByRepository<MariadbCustomers>(sqlClient, MariadbCustomers)
