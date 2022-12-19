/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mssql

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MssqlFloats
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mssqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectFloatRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectFloatTest

class SpringJdbcSelectFloatMssqlTest : AbstractSpringJdbcMssqlTest<FloatRepositoryMssqlSelect>(),
    SelectFloatTest<MssqlFloats, FloatRepositoryMssqlSelect, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<FloatRepositoryMssqlSelect>(resource)
    }

    override val repository: FloatRepositoryMssqlSelect by lazy {
        getContextRepository()
    }
}

class FloatRepositoryMssqlSelect(client: JdbcOperations) :
    SelectFloatRepository<MssqlFloats>(client.sqlClient(mssqlTables), MssqlFloats)
