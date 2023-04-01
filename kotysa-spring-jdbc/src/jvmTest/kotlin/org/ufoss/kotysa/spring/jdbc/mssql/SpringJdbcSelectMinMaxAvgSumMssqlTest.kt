/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mssql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MssqlCustomers
import org.ufoss.kotysa.test.mssqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectMinMaxAvgSumRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectMinMaxAvgSumTest

class SpringJdbcSelectMinMaxAvgSumMssqlTest : AbstractSpringJdbcMssqlTest<MinMaxAvgSumRepositoryMssqlSelect>(),
    SelectMinMaxAvgSumTest<MssqlCustomers, MinMaxAvgSumRepositoryMssqlSelect, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        MinMaxAvgSumRepositoryMssqlSelect(jdbcOperations)
}

class MinMaxAvgSumRepositoryMssqlSelect(client: JdbcOperations) :
    SelectMinMaxAvgSumRepository<MssqlCustomers>(client.sqlClient(mssqlTables), MssqlCustomers)
