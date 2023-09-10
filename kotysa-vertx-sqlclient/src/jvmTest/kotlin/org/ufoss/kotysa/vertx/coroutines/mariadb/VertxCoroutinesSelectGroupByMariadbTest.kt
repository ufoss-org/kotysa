/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mariadb

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MariadbCustomers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectGroupByRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectGroupByTest

class VertxCoroutinesSelectGroupByMariadbTest : AbstractVertxCoroutinesMariadbTest<GroupByRepositoryMariadbSelect>(),
    CoroutinesSelectGroupByTest<MariadbCustomers, GroupByRepositoryMariadbSelect, Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = GroupByRepositoryMariadbSelect(sqlClient)
}

class GroupByRepositoryMariadbSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectGroupByRepository<MariadbCustomers>(sqlClient, MariadbCustomers)
