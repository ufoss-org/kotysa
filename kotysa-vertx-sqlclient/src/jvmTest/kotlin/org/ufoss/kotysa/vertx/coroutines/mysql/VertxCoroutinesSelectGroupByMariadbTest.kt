/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mysql

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MysqlCustomers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectGroupByRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectGroupByTest

class VertxCoroutinesSelectGroupByMysqlTest : AbstractVertxCoroutinesMysqlTest<GroupByRepositoryMysqlSelect>(),
    CoroutinesSelectGroupByTest<MysqlCustomers, GroupByRepositoryMysqlSelect, Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = GroupByRepositoryMysqlSelect(sqlClient)
}

class GroupByRepositoryMysqlSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectGroupByRepository<MysqlCustomers>(sqlClient, MysqlCustomers)
