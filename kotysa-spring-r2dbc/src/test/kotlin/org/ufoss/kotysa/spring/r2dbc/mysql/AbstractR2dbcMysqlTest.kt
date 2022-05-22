/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mysql

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
import org.ufoss.kotysa.spring.r2dbc.R2dbcRepositoryTest
import org.ufoss.kotysa.spring.r2dbc.coSqlClient
import org.ufoss.kotysa.spring.r2dbc.sqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.SpringR2dbcReactorTransactionalOp
import org.ufoss.kotysa.spring.r2dbc.transaction.SpringR2dbcCoroutinesTransactionalOp
import org.ufoss.kotysa.spring.r2dbc.transaction.coTransactionalOp
import org.ufoss.kotysa.spring.r2dbc.transaction.transactionalOp
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.hooks.MySqlContainerExecutionHook
import org.ufoss.kotysa.test.hooks.MySqlContainerResource
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mysqlTables

@ExtendWith(MySqlContainerExecutionHook::class)
@ResourceLock(MySqlContainerResource.ID)
abstract class AbstractR2dbcMysqlTest<T : Repository> : R2dbcRepositoryTest<T> {
    protected lateinit var context: ConfigurableApplicationContext

    protected inline fun <reified U : Repository> startContext(containerResource: TestContainersCloseableResource) =
            application {
                beans {
                    bean<U>()
                    bean { ref<DatabaseClient>().sqlClient(mysqlTables) }
                    bean { ref<DatabaseClient>().coSqlClient(mysqlTables) }
                }
                listener<ApplicationReadyEvent> {
                    ref<U>().init()
                }
                r2dbc {
                    url = "r2dbc:mysql://${containerResource.host}:${containerResource.firstMappedPort}/db"
                    username = "mysql"
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
