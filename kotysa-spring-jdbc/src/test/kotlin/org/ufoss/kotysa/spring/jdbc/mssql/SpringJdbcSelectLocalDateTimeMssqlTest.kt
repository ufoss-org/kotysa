/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mssql

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MssqlLocalDateTimes
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mssqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateTimeRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateTimeTest

class SpringJdbcSelectLocalDateTimeMssqlTest : AbstractSpringJdbcMssqlTest<LocalDateTimeRepositoryMssqlSelect>(),
    SelectLocalDateTimeTest<MssqlLocalDateTimes, LocalDateTimeRepositoryMssqlSelect, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<LocalDateTimeRepositoryMssqlSelect>(resource)
    }

    override val repository: LocalDateTimeRepositoryMssqlSelect by lazy {
        getContextRepository()
    }
}

class LocalDateTimeRepositoryMssqlSelect(client: JdbcOperations) :
    SelectLocalDateTimeRepository<MssqlLocalDateTimes>(client.sqlClient(mssqlTables), MssqlLocalDateTimes)
