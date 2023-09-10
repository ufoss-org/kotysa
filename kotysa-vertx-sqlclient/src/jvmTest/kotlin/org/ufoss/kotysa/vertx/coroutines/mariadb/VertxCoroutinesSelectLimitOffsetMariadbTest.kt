/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mariadb

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MariadbCustomers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLimitOffsetRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLimitOffsetTest

class VertxCoroutinesSelectLimitOffsetMariadbTest : AbstractVertxCoroutinesMariadbTest<LimitOffsetRepositoryMariadbSelect>(),
    CoroutinesSelectLimitOffsetTest<MariadbCustomers, LimitOffsetRepositoryMariadbSelect, Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = LimitOffsetRepositoryMariadbSelect(sqlClient)
}

class LimitOffsetRepositoryMariadbSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectLimitOffsetRepository<MariadbCustomers>(sqlClient, MariadbCustomers)
