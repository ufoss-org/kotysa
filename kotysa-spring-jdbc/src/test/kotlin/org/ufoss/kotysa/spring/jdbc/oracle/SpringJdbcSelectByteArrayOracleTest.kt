/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.oracle

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.OracleByteArrays
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.oracleTables
import org.ufoss.kotysa.test.repositories.blocking.SelectByteArrayRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectByteArrayTest

class SpringJdbcSelectByteArrayOracleTest : AbstractSpringJdbcOracleTest<ByteArrayRepositoryOracleSelect>(),
    SelectByteArrayTest<OracleByteArrays, ByteArrayRepositoryOracleSelect, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<ByteArrayRepositoryOracleSelect>(resource)
    }

    override val repository: ByteArrayRepositoryOracleSelect by lazy {
        getContextRepository()
    }
}

class ByteArrayRepositoryOracleSelect(client: JdbcOperations) :
    SelectByteArrayRepository<OracleByteArrays>(client.sqlClient(oracleTables), OracleByteArrays)
