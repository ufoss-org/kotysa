/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.H2BigDecimals
import org.ufoss.kotysa.test.h2Tables
import org.ufoss.kotysa.test.repositories.blocking.SelectBigDecimalRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectBigDecimalTest

class SpringJdbcSelectBigDecimalH2Test : AbstractSpringJdbcH2Test<BigDecimalRepositoryH2Select>(),
    SelectBigDecimalTest<H2BigDecimals, BigDecimalRepositoryH2Select, SpringJdbcTransaction> {
    override val context = startContext<BigDecimalRepositoryH2Select>()
    override val repository = getContextRepository<BigDecimalRepositoryH2Select>()
}

class BigDecimalRepositoryH2Select(client: JdbcOperations) :
    SelectBigDecimalRepository<H2BigDecimals>(client.sqlClient(h2Tables), H2BigDecimals)
