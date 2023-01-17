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
import org.ufoss.kotysa.test.repositories.blocking.SelectGroupByRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectGroupByTest

class SpringJdbcSelectGroupByOracleTest : AbstractSpringJdbcOracleTest<GroupByRepositoryOracleSelect>(),
    SelectGroupByTest<OracleCustomers, GroupByRepositoryOracleSelect, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<GroupByRepositoryOracleSelect>(resource)
    }

    override val repository: GroupByRepositoryOracleSelect by lazy {
        getContextRepository()
    }
}

class GroupByRepositoryOracleSelect(client: JdbcOperations) :
    SelectGroupByRepository<OracleCustomers>(client.sqlClient(oracleTables), OracleCustomers)
