/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.oracle

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.OracleLocalDates
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalDateRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalDateTest


class R2dbcSelectLocalDateOracleTest : AbstractR2dbcOracleTest<LocalDateRepositoryOracleSelect>(),
    CoroutinesSelectLocalDateTest<OracleLocalDates, LocalDateRepositoryOracleSelect, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = LocalDateRepositoryOracleSelect(sqlClient)
}

class LocalDateRepositoryOracleSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectLocalDateRepository<OracleLocalDates>(sqlClient, OracleLocalDates)
