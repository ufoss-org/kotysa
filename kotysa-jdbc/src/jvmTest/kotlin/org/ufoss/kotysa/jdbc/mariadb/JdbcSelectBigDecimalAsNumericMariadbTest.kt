/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mariadb

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MariadbBigDecimalAsNumerics
import org.ufoss.kotysa.test.repositories.blocking.SelectBigDecimalAsNumericRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectBigDecimalAsNumericTest

class JdbcSelectBigDecimalAsNumericMariadbTest : AbstractJdbcMariadbTest<SelectBigDecimalAsNumericRepositoryMariadbSelect>(),
    SelectBigDecimalAsNumericTest<MariadbBigDecimalAsNumerics, SelectBigDecimalAsNumericRepositoryMariadbSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = SelectBigDecimalAsNumericRepositoryMariadbSelect(sqlClient)
}

class SelectBigDecimalAsNumericRepositoryMariadbSelect(sqlClient: JdbcSqlClient) :
    SelectBigDecimalAsNumericRepository<MariadbBigDecimalAsNumerics>(sqlClient, MariadbBigDecimalAsNumerics)
