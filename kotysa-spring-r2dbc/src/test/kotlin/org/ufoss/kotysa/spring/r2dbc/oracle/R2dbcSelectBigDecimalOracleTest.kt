/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.oracle

import org.junit.jupiter.api.BeforeAll
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.OracleBigDecimals
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectBigDecimalRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectBigDecimalTest

class JdbcSelectBigDecimalOracleTest : AbstractR2dbcOracleTest<ReactorSelectBigDecimalRepositoryOracleSelect>(),
    ReactorSelectBigDecimalTest<OracleBigDecimals, ReactorSelectBigDecimalRepositoryOracleSelect, ReactorTransaction> {
    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<ReactorSelectBigDecimalRepositoryOracleSelect>(resource)
    }

    override val repository: ReactorSelectBigDecimalRepositoryOracleSelect by lazy {
        getContextRepository()
    }
}

class ReactorSelectBigDecimalRepositoryOracleSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectBigDecimalRepository<OracleBigDecimals>(sqlClient, OracleBigDecimals)
