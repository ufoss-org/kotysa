/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.oracle

import org.springframework.dao.DataIntegrityViolationException
import org.ufoss.kotysa.OracleCoroutinesSqlClient
import org.ufoss.kotysa.OracleReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.reactor.ReactorInsertRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorInsertTest

class R2DbcInsertOracleTest : AbstractR2dbcOracleTest<OracleInsertRepository>(),
    ReactorInsertTest<OracleInts, OracleLongs, OracleCustomers, OracleIntNonNullIds, OracleLongNonNullIds,
            OracleInsertRepository, ReactorTransaction> {

    override fun instantiateRepository(sqlClient: OracleReactorSqlClient, coSqlClient: OracleCoroutinesSqlClient) =
        OracleInsertRepository(sqlClient)

    override val exceptionClass = DataIntegrityViolationException::class.java
}

class OracleInsertRepository(sqlClient: ReactorSqlClient) :
    ReactorInsertRepository<OracleInts, OracleLongs, OracleCustomers, OracleIntNonNullIds, OracleLongNonNullIds>(
        sqlClient,
        OracleInts,
        OracleLongs,
        OracleCustomers,
        OracleIntNonNullIds,
        OracleLongNonNullIds
    )
