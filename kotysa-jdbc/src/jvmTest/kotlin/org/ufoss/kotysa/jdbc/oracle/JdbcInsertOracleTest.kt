/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.oracle

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.InsertRepository
import org.ufoss.kotysa.test.repositories.blocking.InsertTest
import java.sql.SQLIntegrityConstraintViolationException

@Order(3)
class JdbcInsertOracleTest : AbstractJdbcOracleTest<RepositoryOracleInsert>(),
    InsertTest<OracleInts, OracleLongs, OracleCustomers, RepositoryOracleInsert, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = RepositoryOracleInsert(sqlClient)
    override val exceptionClass = SQLIntegrityConstraintViolationException::class
}

class RepositoryOracleInsert(sqlClient: JdbcSqlClient) :
    InsertRepository<OracleInts, OracleLongs, OracleCustomers>(sqlClient, OracleInts, OracleLongs, OracleCustomers)
