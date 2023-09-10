/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mysql

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MysqlLocalTimes
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalTimeRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalTimeTest


class VertxCoroutinesSelectLocalTimeMysqlTest : AbstractVertxCoroutinesMysqlTest<LocalTimeRepositoryMysqlSelect>(),
    CoroutinesSelectLocalTimeTest<MysqlLocalTimes, LocalTimeRepositoryMysqlSelect, Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = LocalTimeRepositoryMysqlSelect(sqlClient)
}

class LocalTimeRepositoryMysqlSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectLocalTimeRepository<MysqlLocalTimes>(sqlClient, MysqlLocalTimes)
