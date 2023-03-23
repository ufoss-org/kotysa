/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.h2

import io.r2dbc.spi.ConnectionFactories
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.springframework.beans.factory.getBean
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans
import org.springframework.r2dbc.connection.R2dbcTransactionManager
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.transaction.ReactiveTransactionManager
import org.springframework.transaction.reactive.TransactionalOperator
import org.ufoss.kotysa.H2CoroutinesSqlClient
import org.ufoss.kotysa.H2ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.coSqlClient
import org.ufoss.kotysa.spring.r2dbc.sqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.*
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.h2Tables
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesRepositoryTest
import org.ufoss.kotysa.test.repositories.reactor.ReactorRepositoryTest

abstract class AbstractR2dbcH2Test<T : Repository> : ReactorRepositoryTest<T, ReactorTransaction>,
    CoroutinesRepositoryTest<T, ReactorTransaction> {
    private lateinit var context: GenericApplicationContext

    @BeforeAll
    fun beforeAll() {
        val connectionFactory = ConnectionFactories.get("r2dbc:h2:mem:///testdb;DB_CLOSE_DELAY=-1")
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
        sqlClient: H2ReactorSqlClient,
        coSqlClient: H2CoroutinesSqlClient
    ): T

    override val repository: T by lazy {
        val databaseClient = context.getBean<DatabaseClient>()
        instantiateRepository(databaseClient.sqlClient(h2Tables), databaseClient.coSqlClient(h2Tables))
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
