/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.oracle

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.OracleLocalDates
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalDateRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalDateTest


class VertxCoroutinesSelectLocalDateOracleTest : AbstractVertxCoroutinesOracleTest<LocalDateRepositoryOracleSelect>(),
    CoroutinesSelectLocalDateTest<OracleLocalDates, LocalDateRepositoryOracleSelect, Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = LocalDateRepositoryOracleSelect(sqlClient)
}

class LocalDateRepositoryOracleSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectLocalDateRepository<OracleLocalDates>(sqlClient, OracleLocalDates)
