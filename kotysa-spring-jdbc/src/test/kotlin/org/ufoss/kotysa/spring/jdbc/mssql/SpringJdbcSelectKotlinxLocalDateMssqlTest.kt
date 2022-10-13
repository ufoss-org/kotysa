/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mssql

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MssqlKotlinxLocalDates
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mssqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateTest

class SpringJdbcSelectKotlinxLocalDateMssqlTest : AbstractSpringJdbcMssqlTest<KotlinxLocalDateRepositoryMssqlSelect>(),
    SelectKotlinxLocalDateTest<MssqlKotlinxLocalDates, KotlinxLocalDateRepositoryMssqlSelect, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<KotlinxLocalDateRepositoryMssqlSelect>(resource)
    }

    override val repository: KotlinxLocalDateRepositoryMssqlSelect by lazy {
        getContextRepository()
    }
}

class KotlinxLocalDateRepositoryMssqlSelect(client: JdbcOperations) :
    SelectKotlinxLocalDateRepository<MssqlKotlinxLocalDates>(client.sqlClient(mssqlTables), MssqlKotlinxLocalDates)
