/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.junit.jupiter.api.AfterAll
import org.springframework.beans.factory.getBean
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.fu.kofu.application
import org.springframework.fu.kofu.jdbc.jdbc
import org.testcontainers.containers.PostgreSQLContainer
import org.ufoss.kotysa.test.Repository

class KPostgreSQLContainer : PostgreSQLContainer<KPostgreSQLContainer>()


abstract class AbstractSpringJdbcPostgresqlTest<T : Repository> {

    protected abstract val repository: T

    protected inline fun <reified U : Repository> startContext(): ConfigurableApplicationContext {
        // PostgreSQL testcontainers must be started first to get random Docker mapped port
        val postgresqlContainer = KPostgreSQLContainer()
                .withDatabaseName("postgres")
                .withUsername("postgres")
                .withPassword("")
        postgresqlContainer.start()

        return application {
            beans {
                bean { postgresqlContainer }
                bean<U>()
            }
            listener<ApplicationReadyEvent> {
                ref<U>().init()
            }
            jdbc {
                url = "jdbc:postgresql://${postgresqlContainer.containerIpAddress}:${postgresqlContainer.firstMappedPort}/db"
            }
        }.run()
    }

    protected abstract val context: ConfigurableApplicationContext

    protected inline fun <reified U : Repository> getContextRepository() = context.getBean<U>()

    @AfterAll
    fun afterAll() = context.run {
        repository.delete()
        getBean<PostgreSQLContainer<*>>().stop()
        close()
    }
}
