/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.junit.jupiter.api.Order
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.SelectLongRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLongTest

@Order(2)
class SpringJdbcSelectLongH2Test : AbstractSpringJdbcH2Test<SelectLongRepositoryH2Select>(),
    SelectLongTest<H2Longs, SelectLongRepositoryH2Select, SpringJdbcTransaction> {
    override fun instantiateRepository(jdbcOperations: JdbcOperations) = SelectLongRepositoryH2Select(jdbcOperations)
}

class SelectLongRepositoryH2Select(client: JdbcOperations) : SelectLongRepository<H2Longs>(client.sqlClient(h2Tables), H2Longs)
