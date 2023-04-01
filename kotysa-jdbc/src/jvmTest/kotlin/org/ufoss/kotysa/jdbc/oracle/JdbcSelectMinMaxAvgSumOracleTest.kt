/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.oracle

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.OracleCustomers
import org.ufoss.kotysa.test.repositories.blocking.SelectMinMaxAvgSumRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectMinMaxAvgSumTest

class JdbcSelectMinMaxAvgSumOracleTest : AbstractJdbcOracleTest<MinMaxAvgSumRepositoryOracleSelect>(),
    SelectMinMaxAvgSumTest<OracleCustomers, MinMaxAvgSumRepositoryOracleSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = MinMaxAvgSumRepositoryOracleSelect(sqlClient)
}

class MinMaxAvgSumRepositoryOracleSelect(sqlClient: JdbcSqlClient) :
    SelectMinMaxAvgSumRepository<OracleCustomers>(sqlClient, OracleCustomers)
