/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.oracle

import io.r2dbc.spi.R2dbcDataIntegrityViolationException
import org.junit.jupiter.api.Order
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesInsertRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesInsertTest

@Order(3)
class R2dbcInsertOracleTest : AbstractR2dbcOracleTest<RepositoryOracleInsert>(),
    CoroutinesInsertTest<OracleInts, OracleLongs, OracleCustomers, OracleIntNonNullIds, OracleLongNonNullIds,
            RepositoryOracleInsert, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = RepositoryOracleInsert(sqlClient)
    override val exceptionClass = R2dbcDataIntegrityViolationException::class.java
}

class RepositoryOracleInsert(sqlClient: R2dbcSqlClient) :
    CoroutinesInsertRepository<OracleInts, OracleLongs, OracleCustomers, OracleIntNonNullIds, OracleLongNonNullIds>(
        sqlClient,
        OracleInts,
        OracleLongs,
        OracleCustomers,
        OracleIntNonNullIds,
        OracleLongNonNullIds
    )
