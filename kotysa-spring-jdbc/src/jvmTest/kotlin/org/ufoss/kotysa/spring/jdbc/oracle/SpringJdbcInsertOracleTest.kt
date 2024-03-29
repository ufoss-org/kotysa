/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.oracle

import org.junit.jupiter.api.Order
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.InsertRepository
import org.ufoss.kotysa.test.repositories.blocking.InsertTest

@Order(3)
class SpringJdbcInsertOracleTest : AbstractSpringJdbcOracleTest<RepositoryOracleInsert>(),
    InsertTest<OracleInts, OracleLongs, OracleCustomers, OracleIntNonNullIds, OracleLongNonNullIds,
            RepositoryOracleInsert, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) = RepositoryOracleInsert(jdbcOperations)

    override val exceptionClass = DataIntegrityViolationException::class
}

class RepositoryOracleInsert(dbClient: JdbcOperations) :
    InsertRepository<OracleInts, OracleLongs, OracleCustomers, OracleIntNonNullIds, OracleLongNonNullIds>(
        dbClient.sqlClient(oracleTables),
        OracleInts,
        OracleLongs,
        OracleCustomers,
        OracleIntNonNullIds,
        OracleLongNonNullIds
    )
