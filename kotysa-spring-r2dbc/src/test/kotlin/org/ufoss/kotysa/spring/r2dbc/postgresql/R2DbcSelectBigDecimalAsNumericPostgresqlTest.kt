/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.postgresql

import org.junit.jupiter.api.BeforeAll
import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.spring.r2dbc.sqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.PostgresqlBigDecimalAsNumerics
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.postgresqlTables
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectBigDecimalAsNumericRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectBigDecimalAsNumericTest

class R2DbcSelectBigDecimalAsNumericPostgresqlTest : AbstractR2dbcPostgresqlTest<BigDecimalAsNumericPostgresqlRepository>(),
    ReactorSelectBigDecimalAsNumericTest<PostgresqlBigDecimalAsNumerics, BigDecimalAsNumericPostgresqlRepository,
            ReactorTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<BigDecimalAsNumericPostgresqlRepository>(resource)
    }

    override val repository: BigDecimalAsNumericPostgresqlRepository by lazy {
        getContextRepository()
    }
}

class BigDecimalAsNumericPostgresqlRepository(client: DatabaseClient) :
    ReactorSelectBigDecimalAsNumericRepository<PostgresqlBigDecimalAsNumerics>(
        client.sqlClient(postgresqlTables),
        PostgresqlBigDecimalAsNumerics
    )
