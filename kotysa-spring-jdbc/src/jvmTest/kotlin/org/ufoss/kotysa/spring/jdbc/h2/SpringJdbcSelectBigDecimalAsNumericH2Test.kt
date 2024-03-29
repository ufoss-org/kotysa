/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.H2BigDecimalAsNumerics
import org.ufoss.kotysa.test.h2Tables
import org.ufoss.kotysa.test.repositories.blocking.SelectBigDecimalAsNumericRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectBigDecimalAsNumericTest

class SpringJdbcSelectBigDecimalAsNumericH2Test : AbstractSpringJdbcH2Test<BigDecimalAsNumericRepositoryH2Select>(),
    SelectBigDecimalAsNumericTest<H2BigDecimalAsNumerics, BigDecimalAsNumericRepositoryH2Select, SpringJdbcTransaction> {
    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        BigDecimalAsNumericRepositoryH2Select(jdbcOperations)
}

class BigDecimalAsNumericRepositoryH2Select(client: JdbcOperations) :
    SelectBigDecimalAsNumericRepository<H2BigDecimalAsNumerics>(client.sqlClient(h2Tables), H2BigDecimalAsNumerics)
