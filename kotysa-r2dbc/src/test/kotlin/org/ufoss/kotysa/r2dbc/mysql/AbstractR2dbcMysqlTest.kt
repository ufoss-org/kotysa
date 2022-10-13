/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactoryOptions
import io.r2dbc.spi.Option
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.parallel.ResourceLock
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.r2dbc.sqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.hooks.MySqlContainerExecutionHook
import org.ufoss.kotysa.test.hooks.MySqlContainerResource
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mysqlTables
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesRepositoryTest

@ExtendWith(MySqlContainerExecutionHook::class)
@ResourceLock(MySqlContainerResource.ID)
@Tag("r2dbc-testcontainers")
abstract class AbstractR2dbcMysqlTest<T : Repository> : CoroutinesRepositoryTest<T, R2dbcTransaction> {
    private lateinit var sqlClient: R2dbcSqlClient

    @BeforeAll
    fun beforeAll(containerResource: TestContainersCloseableResource) {
        val options = ConnectionFactoryOptions.builder()
            .option(ConnectionFactoryOptions.DRIVER, "mysql")
            .option(ConnectionFactoryOptions.HOST, containerResource.host)
            .option(ConnectionFactoryOptions.PORT, containerResource.firstMappedPort)
            .option(ConnectionFactoryOptions.DATABASE, "db")
            .option(Option.valueOf("disableMariaDbDriver"), "")
            .option(ConnectionFactoryOptions.USER, "mysql")
            .option(ConnectionFactoryOptions.PASSWORD, "test")
            .build()
        sqlClient = ConnectionFactories.get(options).sqlClient(mysqlTables)
        repository.init()
    }

    protected abstract fun instantiateRepository(sqlClient: R2dbcSqlClient): T

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
