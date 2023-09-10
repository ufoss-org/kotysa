/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mariadb

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MariadbLocalDates
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalDateRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalDateTest


class VertxCoroutinesSelectLocalDateMariadbTest : AbstractVertxCoroutinesMariadbTest<LocalDateRepositoryMariadbSelect>(),
    CoroutinesSelectLocalDateTest<MariadbLocalDates, LocalDateRepositoryMariadbSelect, Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = LocalDateRepositoryMariadbSelect(sqlClient)
}

class LocalDateRepositoryMariadbSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectLocalDateRepository<MariadbLocalDates>(sqlClient, MariadbLocalDates)
