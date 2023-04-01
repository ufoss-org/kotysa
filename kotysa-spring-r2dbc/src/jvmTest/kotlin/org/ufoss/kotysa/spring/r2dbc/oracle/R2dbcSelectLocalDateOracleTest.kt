/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.oracle

import org.ufoss.kotysa.OracleCoroutinesSqlClient
import org.ufoss.kotysa.OracleReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.OracleLocalDates
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLocalDateRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLocalDateTest

class JdbcSelectLocalDateOracleTest : AbstractR2dbcOracleTest<LocalDateRepositoryOracleSelect>(),
    ReactorSelectLocalDateTest<OracleLocalDates, LocalDateRepositoryOracleSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: OracleReactorSqlClient, coSqlClient: OracleCoroutinesSqlClient) =
        LocalDateRepositoryOracleSelect(sqlClient)
}

class LocalDateRepositoryOracleSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectLocalDateRepository<OracleLocalDates>(sqlClient, OracleLocalDates)
