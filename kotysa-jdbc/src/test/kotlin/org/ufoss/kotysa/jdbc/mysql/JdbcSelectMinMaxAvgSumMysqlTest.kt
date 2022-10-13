/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mysql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MysqlCustomers
import org.ufoss.kotysa.test.repositories.blocking.SelectMinMaxAvgSumRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectMinMaxAvgSumTest

class JdbcSelectMinMaxAvgSumMysqlTest : AbstractJdbcMysqlTest<MinMaxAvgSumRepositoryMysqlSelect>(),
    SelectMinMaxAvgSumTest<MysqlCustomers, MinMaxAvgSumRepositoryMysqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = MinMaxAvgSumRepositoryMysqlSelect(sqlClient)
}

class MinMaxAvgSumRepositoryMysqlSelect(sqlClient: JdbcSqlClient) :
    SelectMinMaxAvgSumRepository<MysqlCustomers>(sqlClient, MysqlCustomers)
