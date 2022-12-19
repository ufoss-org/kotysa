/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.postgresql

import org.junit.jupiter.api.BeforeAll
import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.spring.r2dbc.sqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.PostgresqlBigDecimals
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.postgresqlTables
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectBigDecimalRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectBigDecimalTest

class R2DbcSelectBigDecimalPostgresqlTest : AbstractR2dbcPostgresqlTest<BigDecimalPostgresqlRepository>(),
    ReactorSelectBigDecimalTest<PostgresqlBigDecimals, BigDecimalPostgresqlRepository,
            ReactorTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<BigDecimalPostgresqlRepository>(resource)
    }

    override val repository: BigDecimalPostgresqlRepository by lazy {
        getContextRepository()
    }
}

class BigDecimalPostgresqlRepository(client: DatabaseClient) :
    ReactorSelectBigDecimalRepository<PostgresqlBigDecimals>(
        client.sqlClient(postgresqlTables),
        PostgresqlBigDecimals
    )
