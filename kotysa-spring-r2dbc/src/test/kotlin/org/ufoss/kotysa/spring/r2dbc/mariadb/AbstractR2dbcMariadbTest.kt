/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mariadb

import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactoryOptions
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.parallel.ResourceLock
import org.springframework.beans.factory.getBean
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans
import org.springframework.r2dbc.connection.R2dbcTransactionManager
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.transaction.ReactiveTransactionManager
import org.springframework.transaction.reactive.TransactionalOperator
import org.ufoss.kotysa.MariadbCoroutinesSqlClient
import org.ufoss.kotysa.MariadbReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.coSqlClient
import org.ufoss.kotysa.spring.r2dbc.sqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.*
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.hooks.*
import org.ufoss.kotysa.test.mariadbTables
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesRepositoryTest
import org.ufoss.kotysa.test.repositories.reactor.ReactorRepositoryTest

@ExtendWith(MariadbContainerExecutionHook::class)
@ResourceLock(MariadbContainerResource.ID)
abstract class AbstractR2dbcMariadbTest<T : Repository> : ReactorRepositoryTest<T, ReactorTransaction>,
    CoroutinesRepositoryTest<T, ReactorTransaction> {
    private lateinit var context: GenericApplicationContext

    @BeforeAll
    fun beforeAll(containerResource: TestContainersCloseableResource) {
        val options = ConnectionFactoryOptions.builder()
            .option(ConnectionFactoryOptions.DRIVER, "mariadb")
            .option(ConnectionFactoryOptions.HOST, containerResource.host)
            .option(ConnectionFactoryOptions.PORT, containerResource.firstMappedPort)
            .option(ConnectionFactoryOptions.DATABASE, "db")
            .option(ConnectionFactoryOptions.USER, "mariadb")
            .option(ConnectionFactoryOptions.PASSWORD, "test")
            .build()
        val connectionFactory = ConnectionFactories.get(options)
        val beans = beans {
            bean { DatabaseClient.create(connectionFactory) }
            bean {
                R2dbcTransactionManager(connectionFactory)
            }
        }
        context = GenericApplicationContext().apply {
            beans.initialize(this)
            refresh()
        }
        repository.init()
    }

    protected abstract fun instantiateRepository(
        sqlClient: MariadbReactorSqlClient,
        coSqlClient: MariadbCoroutinesSqlClient
    ): T

    override val repository: T by lazy {
        val databaseClient = context.getBean<DatabaseClient>()
        instantiateRepository(databaseClient.sqlClient(mariadbTables), databaseClient.coSqlClient(mariadbTables))
    }

    override val operator: SpringR2dbcReactorTransactionalOp by lazy {
        val transactionManager = context.getBean<ReactiveTransactionManager>()
        TransactionalOperator.create(transactionManager).transactionalOp()
    }

    override val coOperator: SpringR2dbcCoroutinesTransactionalOp by lazy {
        val transactionManager = context.getBean<ReactiveTransactionManager>()
        TransactionalOperator.create(transactionManager).coTransactionalOp()
    }

    @AfterAll
    fun afterAll() {
        repository.delete()
        context.close()
    }
}
