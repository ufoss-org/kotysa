/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mariadb

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MariadbCustomers
import org.ufoss.kotysa.test.repositories.blocking.SelectMinMaxAvgSumRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectMinMaxAvgSumTest

class JdbcSelectMinMaxAvgSumMariadbTest : AbstractJdbcMariadbTest<MinMaxAvgSumRepositoryMariadbSelect>(),
    SelectMinMaxAvgSumTest<MariadbCustomers, MinMaxAvgSumRepositoryMariadbSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = MinMaxAvgSumRepositoryMariadbSelect(sqlClient)
}

class MinMaxAvgSumRepositoryMariadbSelect(sqlClient: JdbcSqlClient) :
    SelectMinMaxAvgSumRepository<MariadbCustomers>(sqlClient, MariadbCustomers)
