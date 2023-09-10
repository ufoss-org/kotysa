/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mysql

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MysqlDoubles
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectDoubleRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectDoubleTest

class VertxCoroutinesSelectDoubleMysqlTest : AbstractVertxCoroutinesMysqlTest<DoubleRepositoryMysqlSelect>(),
    CoroutinesSelectDoubleTest<MysqlDoubles, DoubleRepositoryMysqlSelect,
            Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = DoubleRepositoryMysqlSelect(sqlClient)
}

class DoubleRepositoryMysqlSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectDoubleRepository<MysqlDoubles>(sqlClient, MysqlDoubles)
