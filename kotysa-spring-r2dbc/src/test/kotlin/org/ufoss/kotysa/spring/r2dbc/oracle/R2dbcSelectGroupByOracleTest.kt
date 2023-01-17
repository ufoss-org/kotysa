/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.oracle

import org.junit.jupiter.api.BeforeAll
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.OracleCustomers
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectGroupByRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectGroupByTest

class JdbcSelectGroupByOracleTest : AbstractR2dbcOracleTest<GroupByRepositoryOracleSelect>(),
    ReactorSelectGroupByTest<OracleCustomers, GroupByRepositoryOracleSelect, ReactorTransaction> {
    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<GroupByRepositoryOracleSelect>(resource)
    }

    override val repository: GroupByRepositoryOracleSelect by lazy {
        getContextRepository()
    }
}

class GroupByRepositoryOracleSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectGroupByRepository<OracleCustomers>(sqlClient, OracleCustomers)
