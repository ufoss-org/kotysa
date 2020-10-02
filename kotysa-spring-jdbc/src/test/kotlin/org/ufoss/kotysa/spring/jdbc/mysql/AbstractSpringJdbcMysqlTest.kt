/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.junit.jupiter.api.AfterAll
import org.springframework.beans.factory.getBean
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.fu.kofu.application
import org.springframework.fu.kofu.jdbc.jdbc
import org.testcontainers.containers.MySQLContainer
import org.ufoss.kotysa.test.Repository

class KMySQLContainer : MySQLContainer<KMySQLContainer>()


abstract class AbstractSpringJdbcMysqlTest<T : Repository> {

    protected abstract val repository: T

    protected inline fun <reified U : Repository> startContext(): ConfigurableApplicationContext {
        // MySQL testcontainers must be started first to get random Docker mapped port
        val mysqlContainer = KMySQLContainer()
                .withDatabaseName("mysql")
                .withUsername("mysql")
                .withPassword("test")
        mysqlContainer.start()

        return application {
            beans {
                bean { mysqlContainer }
                bean<U>()
            }
            listener<ApplicationReadyEvent> {
                ref<U>().init()
            }
            jdbc {
                url = "jdbc:mysql://${mysqlContainer.containerIpAddress}:${mysqlContainer.firstMappedPort}/db"
            }
        }.run()
    }

    protected abstract val context: ConfigurableApplicationContext

    protected inline fun <reified U : Repository> getContextRepository() = context.getBean<U>()

    @AfterAll
    fun afterAll() = context.run {
        repository.delete()
        getBean<MySQLContainer<*>>().stop()
        close()
    }
}
