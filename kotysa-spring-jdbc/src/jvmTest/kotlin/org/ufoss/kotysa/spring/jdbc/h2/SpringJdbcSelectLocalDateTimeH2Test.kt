/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.H2LocalDateTimes
import org.ufoss.kotysa.test.h2Tables
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateTimeRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateTimeTest

class SpringJdbcSelectLocalDateTimeH2Test : AbstractSpringJdbcH2Test<LocalDateTimeRepositoryH2Select>(),
    SelectLocalDateTimeTest<H2LocalDateTimes, LocalDateTimeRepositoryH2Select, SpringJdbcTransaction> {
    override fun instantiateRepository(jdbcOperations: JdbcOperations) = LocalDateTimeRepositoryH2Select(jdbcOperations)
}

class LocalDateTimeRepositoryH2Select(client: JdbcOperations) :
    SelectLocalDateTimeRepository<H2LocalDateTimes>(client.sqlClient(h2Tables), H2LocalDateTimes)
