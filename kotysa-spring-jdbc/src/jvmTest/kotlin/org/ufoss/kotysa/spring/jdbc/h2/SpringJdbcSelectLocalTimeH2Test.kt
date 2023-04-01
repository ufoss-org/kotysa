/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.H2LocalTimes
import org.ufoss.kotysa.test.h2Tables
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalTimeRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalTimeTest

class SpringJdbcSelectLocalTimeH2Test : AbstractSpringJdbcH2Test<LocalTimeRepositoryH2Select>(),
    SelectLocalTimeTest<H2LocalTimes, LocalTimeRepositoryH2Select, SpringJdbcTransaction> {
    override fun instantiateRepository(jdbcOperations: JdbcOperations) = LocalTimeRepositoryH2Select(jdbcOperations)
}

class LocalTimeRepositoryH2Select(client: JdbcOperations) :
    SelectLocalTimeRepository<H2LocalTimes>(client.sqlClient(h2Tables), H2LocalTimes)
