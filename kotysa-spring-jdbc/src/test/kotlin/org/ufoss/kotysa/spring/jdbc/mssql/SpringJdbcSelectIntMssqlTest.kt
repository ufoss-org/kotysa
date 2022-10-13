/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mssql

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Order
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.repositories.blocking.SelectIntRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectIntTest

@Order(1)
class SpringJdbcSelectIntMssqlTest : AbstractSpringJdbcMssqlTest<SelectIntRepositoryMssqlSelect>(),
    SelectIntTest<MssqlInts, SelectIntRepositoryMssqlSelect, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<SelectIntRepositoryMssqlSelect>(resource)
    }

    override val repository: SelectIntRepositoryMssqlSelect by lazy {
        getContextRepository()
    }
}


class SelectIntRepositoryMssqlSelect(client: JdbcOperations) :
    SelectIntRepository<MssqlInts>(client.sqlClient(mssqlTables), MssqlInts)
