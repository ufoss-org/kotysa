/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mssql

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MssqlBigDecimalAsNumerics
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mssqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectBigDecimalAsNumericRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectBigDecimalAsNumericTest

class SpringJdbcSelectBigDecimalAsNumericMssqlTest : AbstractSpringJdbcMssqlTest<BigDecimalAsNumericRepositoryMssqlSelect>(),
    SelectBigDecimalAsNumericTest<MssqlBigDecimalAsNumerics, BigDecimalAsNumericRepositoryMssqlSelect, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<BigDecimalAsNumericRepositoryMssqlSelect>(resource)
    }

    override val repository: BigDecimalAsNumericRepositoryMssqlSelect by lazy {
        getContextRepository()
    }
}

class BigDecimalAsNumericRepositoryMssqlSelect(client: JdbcOperations) :
    SelectBigDecimalAsNumericRepository<MssqlBigDecimalAsNumerics>(client.sqlClient(mssqlTables), MssqlBigDecimalAsNumerics)
