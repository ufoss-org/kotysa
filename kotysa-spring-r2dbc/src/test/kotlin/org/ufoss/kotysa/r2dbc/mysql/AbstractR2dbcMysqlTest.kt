/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import org.junit.jupiter.api.AfterAll
import org.springframework.beans.factory.getBean
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.fu.kofu.application
import org.springframework.fu.kofu.r2dbc.r2dbc
import org.springframework.r2dbc.core.DatabaseClient
import org.testcontainers.containers.MySQLContainer
import org.ufoss.kotysa.r2dbc.sqlClient
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.mysqlTables

class KMySQLContainer : MySQLContainer<KMySQLContainer>("mysql:8.0.22")


abstract class AbstractR2dbcMysqlTest<T : Repository> {

    protected abstract val repository: T

    protected inline fun <reified U : Repository> startContext(): ConfigurableApplicationContext {
        // PostgreSQL testcontainers must be started first to get random Docker mapped port
        val mysqlContainer = KMySQLContainer()
                .withDatabaseName("db")
                .withUsername("mysql")
                .withPassword("test")
        mysqlContainer.start()

        return application {
            beans {
                bean { mysqlContainer }
                bean<U>()
                bean { ref<DatabaseClient>().sqlClient(mysqlTables) }
            }
            listener<ApplicationReadyEvent> {
                ref<U>().init()
            }
            r2dbc {
                url = "r2dbc:mysql://${mysqlContainer.containerIpAddress}:${mysqlContainer.firstMappedPort}/db"
                username = "mysql"
                password = "test"
                transactional = true
            }
        }.run()
    }

    protected abstract val context: ConfigurableApplicationContext

    protected inline fun <reified U : Repository> getContextRepository() = context.getBean<U>()

    @AfterAll
    fun afterAll() {
        repository.delete()
        context.getBean<MySQLContainer<*>>().stop()
        context.close()
    }
}
