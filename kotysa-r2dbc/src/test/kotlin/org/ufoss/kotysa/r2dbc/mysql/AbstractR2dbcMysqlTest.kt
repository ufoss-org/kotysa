/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import io.r2dbc.spi.Connection
import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactoryOptions
import io.r2dbc.spi.Option
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.parallel.ResourceLock
import org.ufoss.kotysa.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.r2dbc.transaction.R2dbcTransactionalOp
import org.ufoss.kotysa.r2dbc.transaction.transactionalOp
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.hooks.MySqlContainerExecutionHook
import org.ufoss.kotysa.test.hooks.MySqlContainerResource
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.repositories.CoroutinesRepositoryTest

@ExtendWith(MySqlContainerExecutionHook::class)
@ResourceLock(MySqlContainerResource.ID)
@Tag("r2dbc-testcontainers")
abstract class AbstractR2dbcMysqlTest<T : Repository> : CoroutinesRepositoryTest<T, R2dbcTransaction> {
    private lateinit var connection: Connection

    @BeforeAll
    fun beforeAll(containerResource: TestContainersCloseableResource) = runBlocking {
        val options = ConnectionFactoryOptions.builder()
            .option(ConnectionFactoryOptions.DRIVER, "mysql")
            .option(ConnectionFactoryOptions.HOST, containerResource.containerIpAddress)
            .option(ConnectionFactoryOptions.PORT, containerResource.firstMappedPort)
            .option(ConnectionFactoryOptions.DATABASE, "db")
            .option(Option.valueOf("disableMariaDbDriver"), "")
            .option(ConnectionFactoryOptions.USER, "mysql")
            .option(ConnectionFactoryOptions.PASSWORD, "test")
            .build()
        connection = ConnectionFactories.get(options).create().awaitSingle()
        repository.init()
    }

    protected abstract fun instantiateRepository(connection: Connection): T

    override val operator: R2dbcTransactionalOp by lazy {
        connection.transactionalOp()
    }

    override val repository: T by lazy {
        instantiateRepository(connection)
    }

    @AfterAll
    fun afterAll() = runBlocking<Unit> {
        repository.delete()
        connection.close().awaitFirstOrNull()
    }
}
