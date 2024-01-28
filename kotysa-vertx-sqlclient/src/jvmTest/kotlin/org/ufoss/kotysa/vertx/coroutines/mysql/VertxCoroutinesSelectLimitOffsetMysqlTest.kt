/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mysql

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MysqlCustomers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLimitOffsetRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLimitOffsetTest

class VertxCoroutinesSelectLimitOffsetMysqlTest : AbstractVertxCoroutinesMysqlTest<LimitOffsetRepositoryMysqlSelect>(),
    CoroutinesSelectLimitOffsetTest<MysqlCustomers, LimitOffsetRepositoryMysqlSelect, Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = LimitOffsetRepositoryMysqlSelect(sqlClient)
}

class LimitOffsetRepositoryMysqlSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectLimitOffsetRepository<MysqlCustomers>(sqlClient, MysqlCustomers)
