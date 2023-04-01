/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mssql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MssqlBigDecimals
import org.ufoss.kotysa.test.mssqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectBigDecimalRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectBigDecimalTest

class SpringJdbcSelectBigDecimalMssqlTest : AbstractSpringJdbcMssqlTest<BigDecimalRepositoryMssqlSelect>(),
    SelectBigDecimalTest<MssqlBigDecimals, BigDecimalRepositoryMssqlSelect, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) = BigDecimalRepositoryMssqlSelect(jdbcOperations)
}

class BigDecimalRepositoryMssqlSelect(client: JdbcOperations) :
    SelectBigDecimalRepository<MssqlBigDecimals>(client.sqlClient(mssqlTables), MssqlBigDecimals)
