/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mssql

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MssqlLocalDates
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mssqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateTest

class SpringJdbcSelectLocalDateMssqlTest : AbstractSpringJdbcMssqlTest<LocalDateRepositoryMssqlSelect>(),
    SelectLocalDateTest<MssqlLocalDates, LocalDateRepositoryMssqlSelect, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<LocalDateRepositoryMssqlSelect>(resource)
    }

    override val repository: LocalDateRepositoryMssqlSelect by lazy {
        getContextRepository()
    }
}

class LocalDateRepositoryMssqlSelect(client: JdbcOperations) :
    SelectLocalDateRepository<MssqlLocalDates>(client.sqlClient(mssqlTables), MssqlLocalDates)
