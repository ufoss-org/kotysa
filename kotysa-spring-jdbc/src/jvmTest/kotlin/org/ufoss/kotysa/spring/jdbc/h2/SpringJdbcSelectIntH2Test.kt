/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.junit.jupiter.api.Order
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.SelectIntRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectIntTest

@Order(1)
class SpringJdbcSelectIntH2Test : AbstractSpringJdbcH2Test<SelectIntRepositoryH2Select>(),
    SelectIntTest<H2Ints, SelectIntRepositoryH2Select, SpringJdbcTransaction> {
    override fun instantiateRepository(jdbcOperations: JdbcOperations) = SelectIntRepositoryH2Select(jdbcOperations)
}

class SelectIntRepositoryH2Select(client: JdbcOperations) : SelectIntRepository<H2Ints>(client.sqlClient(h2Tables), H2Ints)
