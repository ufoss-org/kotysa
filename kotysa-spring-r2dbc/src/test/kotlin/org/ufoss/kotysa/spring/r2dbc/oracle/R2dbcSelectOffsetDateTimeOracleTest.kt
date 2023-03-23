/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.oracle

import org.ufoss.kotysa.OracleCoroutinesSqlClient
import org.ufoss.kotysa.OracleReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.OracleOffsetDateTimes
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectOffsetDateTimeRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectOffsetDateTimeTest

class JdbcSelectOffsetDateTimeOracleTest : AbstractR2dbcOracleTest<OffsetDateTimeRepositoryOracleSelect>(),
    ReactorSelectOffsetDateTimeTest<OracleOffsetDateTimes, OffsetDateTimeRepositoryOracleSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: OracleReactorSqlClient, coSqlClient: OracleCoroutinesSqlClient) =
        OffsetDateTimeRepositoryOracleSelect(sqlClient)
}

class OffsetDateTimeRepositoryOracleSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectOffsetDateTimeRepository<OracleOffsetDateTimes>(sqlClient, OracleOffsetDateTimes)
