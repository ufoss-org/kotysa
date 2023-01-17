/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.oracle

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.OracleDoubles
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.oracleTables
import org.ufoss.kotysa.test.repositories.blocking.SelectDoubleRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectDoubleTest

class SpringJdbcSelectDoubleOracleTest : AbstractSpringJdbcOracleTest<DoubleRepositoryOracleSelect>(),
    SelectDoubleTest<OracleDoubles, DoubleRepositoryOracleSelect, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<DoubleRepositoryOracleSelect>(resource)
    }

    override val repository: DoubleRepositoryOracleSelect by lazy {
        getContextRepository()
    }
}

class DoubleRepositoryOracleSelect(client: JdbcOperations) :
    SelectDoubleRepository<OracleDoubles>(client.sqlClient(oracleTables), OracleDoubles)
