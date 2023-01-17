/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.oracle

import org.junit.jupiter.api.BeforeAll
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.OracleByteArrays
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectByteArrayRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectByteArrayTest

class JdbcSelectByteArrayOracleTest : AbstractR2dbcOracleTest<ByteArrayRepositoryOracleSelect>(),
    ReactorSelectByteArrayTest<OracleByteArrays, ByteArrayRepositoryOracleSelect, ReactorTransaction> {
    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<ByteArrayRepositoryOracleSelect>(resource)
    }

    override val repository: ByteArrayRepositoryOracleSelect by lazy {
        getContextRepository()
    }
}

class ByteArrayRepositoryOracleSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectByteArrayRepository<OracleByteArrays>(sqlClient, OracleByteArrays)
