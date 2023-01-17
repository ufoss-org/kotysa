/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.oracle

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.OracleCustomers
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.oracleTables
import org.ufoss.kotysa.test.repositories.blocking.SelectMinMaxAvgSumRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectMinMaxAvgSumTest

class SpringJdbcSelectMinMaxAvgSumOracleTest : AbstractSpringJdbcOracleTest<MinMaxAvgSumRepositoryOracleSelect>(),
    SelectMinMaxAvgSumTest<OracleCustomers, MinMaxAvgSumRepositoryOracleSelect, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<MinMaxAvgSumRepositoryOracleSelect>(resource)
    }

    override val repository: MinMaxAvgSumRepositoryOracleSelect by lazy {
        getContextRepository()
    }
}

class MinMaxAvgSumRepositoryOracleSelect(client: JdbcOperations) :
    SelectMinMaxAvgSumRepository<OracleCustomers>(client.sqlClient(oracleTables), OracleCustomers)
