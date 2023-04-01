/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.oracle

import io.vertx.oracleclient.OracleException
import org.junit.jupiter.api.Order
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinyInsertRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinyInsertTest

@Order(3)
class JdbcInsertOracleTest : AbstractVertxSqlClientOracleTest<RepositoryOracleInsert>(),
    MutinyInsertTest<OracleInts, OracleLongs, OracleCustomers, RepositoryOracleInsert> {
    override fun instantiateRepository(sqlClient: VertxSqlClient) = RepositoryOracleInsert(sqlClient)
    override val exceptionClass = OracleException::class.java
}

class RepositoryOracleInsert(sqlClient: VertxSqlClient) :
    MutinyInsertRepository<OracleInts, OracleLongs, OracleCustomers>(
        sqlClient,
        OracleInts,
        OracleLongs,
        OracleCustomers
    )
