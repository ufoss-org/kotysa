/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import org.ufoss.kotysa.test.Repository
import org.junit.jupiter.api.AfterAll
import org.springframework.beans.factory.getBean
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.fu.kofu.application
import org.springframework.fu.kofu.r2dbc.r2dbc
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.transaction.reactive.TransactionalOperator
import org.ufoss.kotysa.r2dbc.coSqlClient
import org.ufoss.kotysa.r2dbc.sqlClient
import org.ufoss.kotysa.r2dbc.transactionalOp
import org.ufoss.kotysa.test.h2Tables


abstract class AbstractR2dbcH2Test<T : Repository> {

    protected abstract val repository: T

    protected inline fun <reified U : Repository> startContext() =
            application {
                beans {
                    bean<U>()
                    bean { ref<DatabaseClient>().sqlClient(h2Tables) }
                    bean { ref<DatabaseClient>().coSqlClient(h2Tables) }
                    bean { ref<TransactionalOperator>().transactionalOp() }
                }
                listener<ApplicationReadyEvent> {
                    ref<U>().init()
                }
                r2dbc {
                    url = "r2dbc:h2:mem:///testdb;DB_CLOSE_DELAY=-1"
                    transactional = true
                }
            }.run()

    protected abstract val context: ConfigurableApplicationContext

    protected inline fun <reified U : Repository> getContextRepository() = context.getBean<U>()

    @AfterAll
    fun afterAll() {
        repository.delete()
        context.close()
    }
}
