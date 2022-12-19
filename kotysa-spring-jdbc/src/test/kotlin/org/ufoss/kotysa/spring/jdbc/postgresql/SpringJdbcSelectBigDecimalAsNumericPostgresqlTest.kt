/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.PostgresqlBigDecimalAsNumerics
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.postgresqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectBigDecimalAsNumericRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectBigDecimalAsNumericTest

class SpringJdbcSelectBigDecimalAsNumericPostgresqlTest : AbstractSpringJdbcPostgresqlTest<BigDecimalAsNumericRepositoryPostgresqlSelect>(),
    SelectBigDecimalAsNumericTest<PostgresqlBigDecimalAsNumerics, BigDecimalAsNumericRepositoryPostgresqlSelect, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<BigDecimalAsNumericRepositoryPostgresqlSelect>(resource)
    }

    override val repository: BigDecimalAsNumericRepositoryPostgresqlSelect by lazy {
        getContextRepository()
    }
}

class BigDecimalAsNumericRepositoryPostgresqlSelect(client: JdbcOperations) :
    SelectBigDecimalAsNumericRepository<PostgresqlBigDecimalAsNumerics>(client.sqlClient(postgresqlTables), PostgresqlBigDecimalAsNumerics)
