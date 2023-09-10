/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.oracle

import io.vertx.oracleclient.OracleException
import org.junit.jupiter.api.Order
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinyInsertRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinyInsertTest

@Order(3)
class JdbcInsertOracleTest : AbstractVertxSqlClientOracleTest<RepositoryOracleInsert>(),
    MutinyInsertTest<OracleInts, OracleLongs, OracleCustomers, RepositoryOracleInsert> {
    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = RepositoryOracleInsert(sqlClient)
    override val exceptionClass = OracleException::class.java
}

class RepositoryOracleInsert(sqlClient: MutinyVertxSqlClient) :
    MutinyInsertRepository<OracleInts, OracleLongs, OracleCustomers>(
        sqlClient,
        OracleInts,
        OracleLongs,
        OracleCustomers
    )
