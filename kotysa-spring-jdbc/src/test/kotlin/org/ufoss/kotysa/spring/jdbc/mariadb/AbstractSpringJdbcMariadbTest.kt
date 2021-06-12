/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mariadb

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.parallel.ResourceLock
import org.springframework.beans.factory.getBean
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.fu.kofu.application
import org.springframework.fu.kofu.jdbc.DataSourceType
import org.springframework.fu.kofu.jdbc.jdbc
import org.ufoss.kotysa.spring.jdbc.SpringJdbcRepositoryTest
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.hooks.*


@ExtendWith(MariadbContainerExecutionHook::class)
@ResourceLock(MariadbContainerResource.ID)
@Tag("spring-jdbc-testcontainers")
abstract class AbstractSpringJdbcMariadbTest<T : Repository> : SpringJdbcRepositoryTest<T> {

    protected inline fun <reified U : Repository> startContext(containerResource: TestContainersCloseableResource) =
            application {
                beans {
                    bean<U>()
                }
                listener<ApplicationReadyEvent> {
                    ref<U>().init()
                }
                jdbc(DataSourceType.Hikari) {
                    url = "jdbc:mariadb://${containerResource.containerIpAddress}:${containerResource.firstMappedPort}/db"
                    username = "mariadb"
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
