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
import org.ufoss.kotysa.test.repositories.blocking.SelectLimitOffsetRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLimitOffsetTest

class SpringJdbcSelectLimitOffsetOracleTest : AbstractSpringJdbcOracleTest<LimitOffsetRepositoryOracleSelect>(),
    SelectLimitOffsetTest<OracleCustomers, LimitOffsetRepositoryOracleSelect, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<LimitOffsetRepositoryOracleSelect>(resource)
    }

    override val repository: LimitOffsetRepositoryOracleSelect by lazy {
        getContextRepository()
    }
}

class LimitOffsetRepositoryOracleSelect(client: JdbcOperations) :
    SelectLimitOffsetRepository<OracleCustomers>(client.sqlClient(oracleTables), OracleCustomers)
