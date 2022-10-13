/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.postgresql

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.parallel.ResourceLock
import org.springframework.beans.factory.getBean
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.fu.kofu.application
import org.springframework.fu.kofu.r2dbc.r2dbc
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.transaction.reactive.TransactionalOperator
import org.ufoss.kotysa.test.repositories.reactor.ReactorRepositoryTest
import org.ufoss.kotysa.spring.r2dbc.coSqlClient
import org.ufoss.kotysa.spring.r2dbc.sqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.*
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.hooks.PostgreSqlContainerExecutionHook
import org.ufoss.kotysa.test.hooks.PostgreSqlContainerResource
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.postgresqlTables
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesRepositoryTest

@ExtendWith(PostgreSqlContainerExecutionHook::class)
@ResourceLock(PostgreSqlContainerResource.ID)
abstract class AbstractR2dbcPostgresqlTest<T : Repository> : ReactorRepositoryTest<T, ReactorTransaction>,
    CoroutinesRepositoryTest<T, ReactorTransaction> {
    
    protected lateinit var context: ConfigurableApplicationContext

    protected inline fun <reified U : Repository> startContext(containerResource: TestContainersCloseableResource) =
        application {
            beans {
                bean<U>()
                bean { ref<DatabaseClient>().sqlClient(postgresqlTables) }
                bean { ref<DatabaseClient>().coSqlClient(postgresqlTables) }
            }
            listener<ApplicationReadyEvent> {
                ref<U>().init()
            }
            r2dbc {
                url = "r2dbc:postgresql://${containerResource.host}:${containerResource.firstMappedPort}/db"
                username = "postgres"
                password = "test"
                transactional = true
            }
        }.run()

    protected inline fun <reified U : Repository> getContextRepository() = context.getBean<U>()

    override val operator: SpringR2dbcReactorTransactionalOp by lazy {
        context.getBean<TransactionalOperator>().transactionalOp()
    }

    override val coOperator: SpringR2dbcCoroutinesTransactionalOp by lazy {
        context.getBean<TransactionalOperator>().coTransactionalOp()
    }

    @AfterAll
    fun afterAll() {
        repository.delete()
        context.close()
    }
}
