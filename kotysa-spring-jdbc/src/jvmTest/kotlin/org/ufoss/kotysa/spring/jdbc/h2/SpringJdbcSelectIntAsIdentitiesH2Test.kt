/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.junit.jupiter.api.Order
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.SelectIntAsIdentitiesRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectIntAsIdentitiesTest

@Order(1)
class SpringJdbcSelectIntAsIdentitiesH2Test : AbstractSpringJdbcH2Test<SelectIntAsIdentitiesRepositoryH2Select>(),
    SelectIntAsIdentitiesTest<H2IntAsIdentities, SelectIntAsIdentitiesRepositoryH2Select, SpringJdbcTransaction> {
    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        SelectIntAsIdentitiesRepositoryH2Select(jdbcOperations)
}

class SelectIntAsIdentitiesRepositoryH2Select(client: JdbcOperations) :
    SelectIntAsIdentitiesRepository<H2IntAsIdentities>(client.sqlClient(h2Tables), H2IntAsIdentities)
