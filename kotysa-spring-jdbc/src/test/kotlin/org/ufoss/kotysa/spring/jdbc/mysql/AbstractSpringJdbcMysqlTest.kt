/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.parallel.ResourceLock
import org.springframework.beans.factory.getBean
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.fu.kofu.application
import org.springframework.fu.kofu.jdbc.jdbc
import org.ufoss.kotysa.spring.jdbc.SpringJdbcRepositoryTest
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.hooks.MySqlContainerExecutionHook
import org.ufoss.kotysa.test.hooks.MySqlContainerResource
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource


@ExtendWith(MySqlContainerExecutionHook::class)
@ResourceLock(MySqlContainerResource.ID)
@Tag("spring-jdbc-testcontainers")
abstract class AbstractSpringJdbcMysqlTest<T : Repository> : SpringJdbcRepositoryTest<T> {

    protected inline fun <reified U : Repository> startContext(containerResource: TestContainersCloseableResource) =
            application {
                beans {
                    bean<U>()
                }
                listener<ApplicationReadyEvent> {
                    ref<U>().init()
                }
                jdbc {
                    url = "jdbc:mysql://${containerResource.containerIpAddress}:${containerResource.firstMappedPort}/db"
                    username = "mysql"
                    password = "test"
                }
            }.run()

    override lateinit var context: ConfigurableApplicationContext
    override lateinit var repository: T

    protected inline fun <reified U : Repository> getContextRepository() = context.getBean<U>()

    @AfterAll
    fun afterAll() = context.run {
        repository.delete()
        close()
    }
}
