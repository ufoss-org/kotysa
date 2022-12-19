/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mariadb

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MariadbBigDecimalAsNumerics
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mariadbTables
import org.ufoss.kotysa.test.repositories.blocking.SelectBigDecimalAsNumericRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectBigDecimalAsNumericTest

class SpringJdbcSelectBigDecimalAsNumericMariadbTest : AbstractSpringJdbcMariadbTest<BigDecimalAsNumericRepositoryMariadbSelect>(),
    SelectBigDecimalAsNumericTest<MariadbBigDecimalAsNumerics, BigDecimalAsNumericRepositoryMariadbSelect, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<BigDecimalAsNumericRepositoryMariadbSelect>(resource)
    }

    override val repository: BigDecimalAsNumericRepositoryMariadbSelect by lazy {
        getContextRepository()
    }
}

class BigDecimalAsNumericRepositoryMariadbSelect(client: JdbcOperations) :
    SelectBigDecimalAsNumericRepository<MariadbBigDecimalAsNumerics>(client.sqlClient(mariadbTables), MariadbBigDecimalAsNumerics)
