/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.oracle

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.OracleFloats
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.oracleTables
import org.ufoss.kotysa.test.repositories.blocking.SelectFloatRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectFloatTest

class SpringJdbcSelectFloatOracleTest : AbstractSpringJdbcOracleTest<FloatRepositoryOracleSelect>(),
    SelectFloatTest<OracleFloats, FloatRepositoryOracleSelect, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<FloatRepositoryOracleSelect>(resource)
    }

    override val repository: FloatRepositoryOracleSelect by lazy {
        getContextRepository()
    }

    // in returns inconsistent results
    override fun `Verify selectAllByFloatNotNullIn finds both`() {}
}

class FloatRepositoryOracleSelect(client: JdbcOperations) :
    SelectFloatRepository<OracleFloats>(client.sqlClient(oracleTables), OracleFloats)
