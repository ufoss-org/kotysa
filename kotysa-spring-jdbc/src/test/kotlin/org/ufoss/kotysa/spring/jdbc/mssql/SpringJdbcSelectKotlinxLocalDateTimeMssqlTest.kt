/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mssql

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MssqlKotlinxLocalDateTimes
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mssqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateTimeRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateTimeTest

class SpringJdbcSelectKotlinxLocalDateTimeMssqlTest :
    AbstractSpringJdbcMssqlTest<KotlinxLocalDateTimeRepositoryMssqlSelect>(),
    SelectKotlinxLocalDateTimeTest<MssqlKotlinxLocalDateTimes, KotlinxLocalDateTimeRepositoryMssqlSelect,
            SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<KotlinxLocalDateTimeRepositoryMssqlSelect>(resource)
    }

    override val repository: KotlinxLocalDateTimeRepositoryMssqlSelect by lazy {
        getContextRepository()
    }
}

class KotlinxLocalDateTimeRepositoryMssqlSelect(client: JdbcOperations) :
    SelectKotlinxLocalDateTimeRepository<MssqlKotlinxLocalDateTimes>(
        client.sqlClient(mssqlTables),
        MssqlKotlinxLocalDateTimes
    )
