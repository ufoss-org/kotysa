/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mysql

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MysqlLocalDates
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalDateRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalDateTest


class VertxCoroutinesSelectLocalDateMysqlTest : AbstractVertxCoroutinesMysqlTest<LocalDateRepositoryMysqlSelect>(),
    CoroutinesSelectLocalDateTest<MysqlLocalDates, LocalDateRepositoryMysqlSelect, Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = LocalDateRepositoryMysqlSelect(sqlClient)
}

class LocalDateRepositoryMysqlSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectLocalDateRepository<MysqlLocalDates>(sqlClient, MysqlLocalDates)
