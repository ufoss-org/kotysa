/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.H2Uuids
import org.ufoss.kotysa.test.h2Tables
import org.ufoss.kotysa.test.repositories.blocking.SelectUuidRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectUuidTest

class SpringJdbcSelectUuidH2Test : AbstractSpringJdbcH2Test<UuidRepositoryH2Select>(),
    SelectUuidTest<H2Uuids, UuidRepositoryH2Select, SpringJdbcTransaction> {
    override fun instantiateRepository(jdbcOperations: JdbcOperations) = UuidRepositoryH2Select(jdbcOperations)
}

class UuidRepositoryH2Select(client: JdbcOperations) :
    SelectUuidRepository<H2Uuids>(client.sqlClient(h2Tables), H2Uuids)
