/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.oracle

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.OracleBigDecimals
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.oracleTables
import org.ufoss.kotysa.test.repositories.blocking.SelectBigDecimalRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectBigDecimalTest

class SpringJdbcSelectBigDecimalOracleTest : AbstractSpringJdbcOracleTest<BigDecimalRepositoryOracleSelect>(),
    SelectBigDecimalTest<OracleBigDecimals, BigDecimalRepositoryOracleSelect, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<BigDecimalRepositoryOracleSelect>(resource)
    }

    override val repository: BigDecimalRepositoryOracleSelect by lazy {
        getContextRepository()
    }
}

class BigDecimalRepositoryOracleSelect(client: JdbcOperations) :
    SelectBigDecimalRepository<OracleBigDecimals>(client.sqlClient(oracleTables), OracleBigDecimals)
