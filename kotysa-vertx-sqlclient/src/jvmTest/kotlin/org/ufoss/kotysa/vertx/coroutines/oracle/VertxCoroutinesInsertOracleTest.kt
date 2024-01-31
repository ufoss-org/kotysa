/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.oracle

import io.vertx.oracleclient.OracleException
import org.junit.jupiter.api.Order
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesInsertRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesInsertTest
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient

@Order(3)
class VertxCoroutinesInsertOracleTest : AbstractVertxCoroutinesOracleTest<RepositoryOracleInsert>(),
    CoroutinesInsertTest<OracleInts, OracleLongs, OracleCustomers, OracleIntNonNullIds, OracleLongNonNullIds,
            RepositoryOracleInsert, Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = RepositoryOracleInsert(sqlClient)
    override val exceptionClass = OracleException::class.java
}

class RepositoryOracleInsert(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesInsertRepository<OracleInts, OracleLongs, OracleCustomers, OracleIntNonNullIds, OracleLongNonNullIds>(
        sqlClient,
        OracleInts,
        OracleLongs,
        OracleCustomers,
        OracleIntNonNullIds,
        OracleLongNonNullIds
    )
