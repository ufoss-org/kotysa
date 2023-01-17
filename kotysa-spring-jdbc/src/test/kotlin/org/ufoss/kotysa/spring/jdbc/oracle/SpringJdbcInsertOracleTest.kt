/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.oracle

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Order
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.repositories.blocking.InsertRepository
import org.ufoss.kotysa.test.repositories.blocking.InsertTest

@Order(3)
class SpringJdbcInsertOracleTest : AbstractSpringJdbcOracleTest<RepositoryOracleInsert>(),
    InsertTest<OracleInts, OracleLongs, OracleCustomers, RepositoryOracleInsert, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<RepositoryOracleInsert>(resource)
    }

    override val repository: RepositoryOracleInsert by lazy {
        getContextRepository()
    }

    override val exceptionClass = DataIntegrityViolationException::class.java
}

class RepositoryOracleInsert(dbClient: JdbcOperations) :
    InsertRepository<OracleInts, OracleLongs, OracleCustomers>(
        dbClient.sqlClient(oracleTables),
        OracleInts,
        OracleLongs,
        OracleCustomers
    )
