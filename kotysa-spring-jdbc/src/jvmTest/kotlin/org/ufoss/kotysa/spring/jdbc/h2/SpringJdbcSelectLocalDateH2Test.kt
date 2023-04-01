/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.H2LocalDates
import org.ufoss.kotysa.test.h2Tables
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateTest

class SpringJdbcSelectLocalDateH2Test : AbstractSpringJdbcH2Test<LocalDateRepositoryH2Select>(),
    SelectLocalDateTest<H2LocalDates, LocalDateRepositoryH2Select, SpringJdbcTransaction> {
    override fun instantiateRepository(jdbcOperations: JdbcOperations) = LocalDateRepositoryH2Select(jdbcOperations)
}

class LocalDateRepositoryH2Select(client: JdbcOperations) :
    SelectLocalDateRepository<H2LocalDates>(client.sqlClient(h2Tables), H2LocalDates)
