/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.oracle

import org.ufoss.kotysa.OracleCoroutinesSqlClient
import org.ufoss.kotysa.OracleReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.OracleCustomers
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLimitOffsetRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLimitOffsetTest

class R2dbcSelectLimitOffsetOracleTest : AbstractR2dbcOracleTest<LimitOffsetRepositoryOracleSelect>(),
    ReactorSelectLimitOffsetTest<OracleCustomers, LimitOffsetRepositoryOracleSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: OracleReactorSqlClient, coSqlClient: OracleCoroutinesSqlClient) =
        LimitOffsetRepositoryOracleSelect(sqlClient)
}

class LimitOffsetRepositoryOracleSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectLimitOffsetRepository<OracleCustomers>(sqlClient, OracleCustomers)
