/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.oracle

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.OracleCustomers
import org.ufoss.kotysa.test.oracleTables
import org.ufoss.kotysa.test.repositories.blocking.SelectMinMaxAvgSumRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectMinMaxAvgSumTest

class SpringJdbcSelectMinMaxAvgSumOracleTest : AbstractSpringJdbcOracleTest<MinMaxAvgSumRepositoryOracleSelect>(),
    SelectMinMaxAvgSumTest<OracleCustomers, MinMaxAvgSumRepositoryOracleSelect, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        MinMaxAvgSumRepositoryOracleSelect(jdbcOperations)
}

class MinMaxAvgSumRepositoryOracleSelect(client: JdbcOperations) :
    SelectMinMaxAvgSumRepository<OracleCustomers>(client.sqlClient(oracleTables), OracleCustomers)
