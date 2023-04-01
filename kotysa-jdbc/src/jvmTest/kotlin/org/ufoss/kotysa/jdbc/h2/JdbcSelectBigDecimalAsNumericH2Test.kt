/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.H2BigDecimalAsNumerics
import org.ufoss.kotysa.test.repositories.blocking.SelectBigDecimalAsNumericRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectBigDecimalAsNumericTest

class JdbcSelectBigDecimalAsNumericH2Test : AbstractJdbcH2Test<SelectBigDecimalAsNumericRepositoryH2Select>(),
    SelectBigDecimalAsNumericTest<H2BigDecimalAsNumerics, SelectBigDecimalAsNumericRepositoryH2Select, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = SelectBigDecimalAsNumericRepositoryH2Select(sqlClient)
}

class SelectBigDecimalAsNumericRepositoryH2Select(sqlClient: JdbcSqlClient) : SelectBigDecimalAsNumericRepository<H2BigDecimalAsNumerics>(sqlClient, H2BigDecimalAsNumerics)
