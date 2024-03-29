/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.junit.jupiter.api.Order
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.InsertRepository
import org.ufoss.kotysa.test.repositories.blocking.InsertTest

@Order(3)
class SpringJdbcInsertH2Test : AbstractSpringJdbcH2Test<RepositoryH2Insert>(),
    InsertTest<H2Ints, H2Longs, H2Customers, H2IntNonNullIds, H2LongNonNullIds, RepositoryH2Insert,
            SpringJdbcTransaction> {
    override fun instantiateRepository(jdbcOperations: JdbcOperations) = RepositoryH2Insert(jdbcOperations)

    override val exceptionClass = DataIntegrityViolationException::class
}

class RepositoryH2Insert(dbClient: JdbcOperations) :
    InsertRepository<H2Ints, H2Longs, H2Customers, H2IntNonNullIds, H2LongNonNullIds>(
        dbClient.sqlClient(h2Tables),
        H2Ints,
        H2Longs,
        H2Customers,
        H2IntNonNullIds,
        H2LongNonNullIds
    )
