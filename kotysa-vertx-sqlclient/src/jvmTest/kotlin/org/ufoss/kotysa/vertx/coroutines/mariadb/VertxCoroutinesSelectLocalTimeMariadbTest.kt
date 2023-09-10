/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mariadb

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MariadbLocalTimes
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalTimeRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalTimeTest


class VertxCoroutinesSelectLocalTimeMariadbTest : AbstractVertxCoroutinesMariadbTest<LocalTimeRepositoryMariadbSelect>(),
    CoroutinesSelectLocalTimeTest<MariadbLocalTimes, LocalTimeRepositoryMariadbSelect, Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = LocalTimeRepositoryMariadbSelect(sqlClient)
}

class LocalTimeRepositoryMariadbSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectLocalTimeRepository<MariadbLocalTimes>(sqlClient, MariadbLocalTimes)
