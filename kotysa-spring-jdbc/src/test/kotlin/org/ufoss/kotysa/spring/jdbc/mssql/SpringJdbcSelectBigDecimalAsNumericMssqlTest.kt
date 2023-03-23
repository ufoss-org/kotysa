/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mssql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MssqlBigDecimalAsNumerics
import org.ufoss.kotysa.test.mssqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectBigDecimalAsNumericRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectBigDecimalAsNumericTest

class SpringJdbcSelectBigDecimalAsNumericMssqlTest :
    AbstractSpringJdbcMssqlTest<BigDecimalAsNumericRepositoryMssqlSelect>(),
    SelectBigDecimalAsNumericTest<MssqlBigDecimalAsNumerics, BigDecimalAsNumericRepositoryMssqlSelect, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        BigDecimalAsNumericRepositoryMssqlSelect(jdbcOperations)
}

class BigDecimalAsNumericRepositoryMssqlSelect(client: JdbcOperations) :
    SelectBigDecimalAsNumericRepository<MssqlBigDecimalAsNumerics>(
        client.sqlClient(mssqlTables),
        MssqlBigDecimalAsNumerics
    )
