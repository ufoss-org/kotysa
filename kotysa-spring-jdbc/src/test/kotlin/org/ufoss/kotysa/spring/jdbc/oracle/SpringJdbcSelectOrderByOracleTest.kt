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
import org.ufoss.kotysa.test.repositories.blocking.SelectOrderByRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectOrderByTest

class SpringJdbcSelectOrderByOracleTest : AbstractSpringJdbcOracleTest<OrderByRepositoryOracleSelect>(),
    SelectOrderByTest<OracleCustomers, OrderByRepositoryOracleSelect, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<OrderByRepositoryOracleSelect>(resource)
    }

    override val repository: OrderByRepositoryOracleSelect by lazy {
        getContextRepository()
    }
}

class OrderByRepositoryOracleSelect(client: JdbcOperations) :
    SelectOrderByRepository<OracleCustomers>(client.sqlClient(oracleTables), OracleCustomers)
