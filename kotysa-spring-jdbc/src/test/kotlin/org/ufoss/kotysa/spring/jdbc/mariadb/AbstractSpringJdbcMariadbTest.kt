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
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransactionalOp
import org.ufoss.kotysa.spring.jdbc.transaction.transactionalOp
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.hooks.*
import org.ufoss.kotysa.test.repositories.blocking.RepositoryTest


@ExtendWith(MariadbContainerExecutionHook::class)
@ResourceLock(MariadbContainerResource.ID)
@Tag("spring-jdbc-testcontainers")
abstract class AbstractSpringJdbcMariadbTest<T : Repository> : RepositoryTest<T, SpringJdbcTransaction> {
    protected lateinit var context: ConfigurableApplicationContext

    protected inline fun <reified U : Repository> startContext(containerResource: TestContainersCloseableResource) =
            application {
                beans {
                    bean<U>()
                }
                listener<ApplicationReadyEvent> {
                    ref<U>().init()
                }
                jdbc(DataSourceType.Hikari) {
                    url = "jdbc:mariadb://${containerResource.host}:${containerResource.firstMappedPort}/db"
                    username = "mariadb"
                    password = "test"
                }
            }.run()

    protected inline fun <reified U : Repository> getContextRepository() = context.getBean<U>()

    override val operator: SpringJdbcTransactionalOp by lazy {
        val transactionManager = context.getBean<PlatformTransactionManager>()
        TransactionTemplate(transactionManager).transactionalOp()
    }

    @AfterAll
    fun afterAll() = context.run {
        repository.delete()
        close()
    }
}
