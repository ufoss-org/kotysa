/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.PostgresqlBigDecimals
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.postgresqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectBigDecimalRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectBigDecimalTest

class SpringJdbcSelectBigDecimalPostgresqlTest : AbstractSpringJdbcPostgresqlTest<BigDecimalRepositoryPostgresqlSelect>(),
    SelectBigDecimalTest<PostgresqlBigDecimals, BigDecimalRepositoryPostgresqlSelect, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<BigDecimalRepositoryPostgresqlSelect>(resource)
    }

    override val repository: BigDecimalRepositoryPostgresqlSelect by lazy {
        getContextRepository()
    }
}

class BigDecimalRepositoryPostgresqlSelect(client: JdbcOperations) :
    SelectBigDecimalRepository<PostgresqlBigDecimals>(client.sqlClient(postgresqlTables), PostgresqlBigDecimals)
