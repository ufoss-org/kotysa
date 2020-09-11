/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import org.junit.jupiter.api.AfterAll
import org.springframework.beans.factory.getBean
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.fu.kofu.application
import org.springframework.fu.kofu.r2dbc.r2dbc
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.transaction.reactive.TransactionalOperator
import org.testcontainers.containers.PostgreSQLContainer
import org.ufoss.kotysa.r2dbc.coSqlClient
import org.ufoss.kotysa.r2dbc.sqlClient
import org.ufoss.kotysa.r2dbc.transaction.coTransactionalOp
import org.ufoss.kotysa.r2dbc.transaction.transactionalOp
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.postgresqlTables

class KPostgreSQLContainer : PostgreSQLContainer<KPostgreSQLContainer>()


abstract class AbstractR2dbcPostgresqlTest<T : Repository> {

    protected abstract val repository: T

    protected inline fun <reified U : Repository> startContext(): ConfigurableApplicationContext {
        // PostgreSQL testcontainers must be started first to get random Docker mapped port
        val postgresqlContainer = KPostgreSQLContainer()
                .withDatabaseName("db")
                .withUsername("postgres")
                .withPassword("")
        postgresqlContainer.start()

        return application {
            beans {
                bean { postgresqlContainer }
                bean<U>()
                bean { ref<DatabaseClient>().sqlClient(postgresqlTables) }
                bean { ref<DatabaseClient>().coSqlClient(postgresqlTables) }
                bean { ref<TransactionalOperator>().transactionalOp() }
                bean { ref<TransactionalOperator>().coTransactionalOp() }
            }
            listener<ApplicationReadyEvent> {
                ref<U>().init()
            }
            r2dbc {
                url = "r2dbc:postgresql://${postgresqlContainer.containerIpAddress}:${postgresqlContainer.firstMappedPort}/db"
                username = "postgres"
                transactional = true
            }
        }.run()
    }

    protected abstract val context: ConfigurableApplicationContext

    protected inline fun <reified U : Repository> getContextRepository() = context.getBean<U>()

    @AfterAll
    fun afterAll() {
        repository.delete()
        context.getBean<PostgreSQLContainer<*>>().stop()
        context.close()
    }
}
