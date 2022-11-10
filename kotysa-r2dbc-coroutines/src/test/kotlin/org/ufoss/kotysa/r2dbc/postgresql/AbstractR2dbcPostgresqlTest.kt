/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactoryOptions
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.parallel.ResourceLock
import org.ufoss.kotysa.PostgresqlR2dbcSqlClient
import org.ufoss.kotysa.r2dbc.sqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.hooks.PostgreSqlContainerExecutionHook
import org.ufoss.kotysa.test.hooks.PostgreSqlContainerResource
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.postgresqlTables
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesRepositoryTest

@ExtendWith(PostgreSqlContainerExecutionHook::class)
@ResourceLock(PostgreSqlContainerResource.ID)
@Tag("r2dbc-testcontainers")
abstract class AbstractR2dbcPostgresqlTest<T : Repository> : CoroutinesRepositoryTest<T, R2dbcTransaction> {
    private lateinit var sqlClient: PostgresqlR2dbcSqlClient

    @BeforeAll
    fun beforeAll(containerResource: TestContainersCloseableResource) {
        val options = ConnectionFactoryOptions.builder()
            .option(ConnectionFactoryOptions.DRIVER, "postgresql")
            .option(ConnectionFactoryOptions.HOST, containerResource.host)
            .option(ConnectionFactoryOptions.PORT, containerResource.firstMappedPort)
            .option(ConnectionFactoryOptions.DATABASE, "db")
            .option(ConnectionFactoryOptions.USER, "postgres")
            .option(ConnectionFactoryOptions.PASSWORD, "test")
            .build()
        sqlClient = ConnectionFactories.get(options).sqlClient(postgresqlTables)
        repository.init()
    }

    protected abstract fun instantiateRepository(sqlClient: PostgresqlR2dbcSqlClient): T

    override val coOperator by lazy {
        sqlClient
    }

    override val repository: T by lazy {
        instantiateRepository(sqlClient)
    }

    @AfterAll
    fun afterAll() {
        repository.delete()
    }
}
