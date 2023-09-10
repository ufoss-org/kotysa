/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mariadb

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MariadbLocalDateTimes
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalDateTimeRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalDateTimeTest

class VertxCoroutinesSelectLocalDateTimeMariadbTest : AbstractVertxCoroutinesMariadbTest<LocalDateTimeRepositoryMariadbSelect>(),
    CoroutinesSelectLocalDateTimeTest<MariadbLocalDateTimes, LocalDateTimeRepositoryMariadbSelect, Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = LocalDateTimeRepositoryMariadbSelect(sqlClient)
}

class LocalDateTimeRepositoryMariadbSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectLocalDateTimeRepository<MariadbLocalDateTimes>(sqlClient, MariadbLocalDateTimes)
