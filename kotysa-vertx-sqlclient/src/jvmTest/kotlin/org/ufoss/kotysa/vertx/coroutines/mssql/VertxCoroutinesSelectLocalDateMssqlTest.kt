/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mssql

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MssqlLocalDates
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalDateRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalDateTest


class VertxCoroutinesSelectLocalDateMssqlTest : AbstractVertxCoroutinesMssqlTest<LocalDateRepositoryMssqlSelect>(),
    CoroutinesSelectLocalDateTest<MssqlLocalDates, LocalDateRepositoryMssqlSelect, Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = LocalDateRepositoryMssqlSelect(sqlClient)
}

class LocalDateRepositoryMssqlSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectLocalDateRepository<MssqlLocalDates>(sqlClient, MssqlLocalDates)
