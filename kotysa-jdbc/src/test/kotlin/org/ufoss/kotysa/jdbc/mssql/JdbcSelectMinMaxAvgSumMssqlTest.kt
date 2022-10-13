/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mssql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MssqlCustomers
import org.ufoss.kotysa.test.repositories.blocking.SelectMinMaxAvgSumRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectMinMaxAvgSumTest

class JdbcSelectMinMaxAvgSumMssqlTest : AbstractJdbcMssqlTest<MinMaxAvgSumRepositoryMssqlSelect>(),
    SelectMinMaxAvgSumTest<MssqlCustomers, MinMaxAvgSumRepositoryMssqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = MinMaxAvgSumRepositoryMssqlSelect(sqlClient)
}

class MinMaxAvgSumRepositoryMssqlSelect(sqlClient: JdbcSqlClient) :
    SelectMinMaxAvgSumRepository<MssqlCustomers>(sqlClient, MssqlCustomers)
