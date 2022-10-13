/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.H2Customers
import org.ufoss.kotysa.test.repositories.blocking.SelectMinMaxAvgSumRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectMinMaxAvgSumTest

class JdbcSelectMinMaxAvgSumH2Test : AbstractJdbcH2Test<MinMaxAvgSumRepositoryH2Select>(),
    SelectMinMaxAvgSumTest<H2Customers, MinMaxAvgSumRepositoryH2Select, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = MinMaxAvgSumRepositoryH2Select(sqlClient)
}

class MinMaxAvgSumRepositoryH2Select(sqlClient: JdbcSqlClient) :
    SelectMinMaxAvgSumRepository<H2Customers>(sqlClient, H2Customers)
