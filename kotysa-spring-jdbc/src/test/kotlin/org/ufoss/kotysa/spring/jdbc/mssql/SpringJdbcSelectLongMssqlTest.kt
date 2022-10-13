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
import org.ufoss.kotysa.test.repositories.blocking.SelectLongRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLongTest

@Order(2)
class SpringJdbcSelectLongMssqlTest : AbstractSpringJdbcMssqlTest<SelectLongRepositoryMssqlSelect>(),
    SelectLongTest<MssqlLongs, SelectLongRepositoryMssqlSelect, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<SelectLongRepositoryMssqlSelect>(resource)
    }

    override val repository: SelectLongRepositoryMssqlSelect by lazy {
        getContextRepository()
    }
}

class SelectLongRepositoryMssqlSelect(client: JdbcOperations) :
    SelectLongRepository<MssqlLongs>(client.sqlClient(mssqlTables), MssqlLongs)
