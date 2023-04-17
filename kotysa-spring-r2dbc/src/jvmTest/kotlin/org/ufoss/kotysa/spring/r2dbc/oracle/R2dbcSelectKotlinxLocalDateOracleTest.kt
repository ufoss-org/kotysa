/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.oracle

import org.ufoss.kotysa.OracleCoroutinesSqlClient
import org.ufoss.kotysa.OracleReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.OracleKotlinxLocalDates
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectKotlinxLocalDateRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectKotlinxLocalDateTest

class R2dbcSelectKotlinxLocalDateOracleTest :
    AbstractR2dbcOracleTest<KotlinxLocalDateRepositoryOracleSelect>(),
    ReactorSelectKotlinxLocalDateTest<OracleKotlinxLocalDates, KotlinxLocalDateRepositoryOracleSelect,
            ReactorTransaction> {
    override fun instantiateRepository(sqlClient: OracleReactorSqlClient, coSqlClient: OracleCoroutinesSqlClient) =
        KotlinxLocalDateRepositoryOracleSelect(sqlClient)
}

class KotlinxLocalDateRepositoryOracleSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectKotlinxLocalDateRepository<OracleKotlinxLocalDates>(sqlClient, OracleKotlinxLocalDates)
