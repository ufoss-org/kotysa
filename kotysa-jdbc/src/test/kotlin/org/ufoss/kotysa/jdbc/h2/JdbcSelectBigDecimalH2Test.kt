/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.H2BigDecimals
import org.ufoss.kotysa.test.repositories.blocking.SelectBigDecimalRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectBigDecimalTest

class JdbcSelectBigDecimalH2Test : AbstractJdbcH2Test<SelectBigDecimalRepositoryH2Select>(),
    SelectBigDecimalTest<H2BigDecimals, SelectBigDecimalRepositoryH2Select, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = SelectBigDecimalRepositoryH2Select(sqlClient)
}

class SelectBigDecimalRepositoryH2Select(sqlClient: JdbcSqlClient) : SelectBigDecimalRepository<H2BigDecimals>(sqlClient, H2BigDecimals)
