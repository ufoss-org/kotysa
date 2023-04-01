/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import com.mysql.cj.jdbc.MysqlDataSource
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.parallel.ResourceLock
import org.springframework.beans.factory.getBean
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans
import org.springframework.jdbc.core.JdbcOperations
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransactionalOp
import org.ufoss.kotysa.spring.jdbc.transaction.transactionalOp
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.hooks.MySqlContainerExecutionHook
import org.ufoss.kotysa.test.hooks.MySqlContainerResource
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.repositories.blocking.RepositoryTest


@ExtendWith(MySqlContainerExecutionHook::class)
@ResourceLock(MySqlContainerResource.ID)
@Tag("spring-jdbc-testcontainers")
abstract class AbstractSpringJdbcMysqlTest<T : Repository> : RepositoryTest<T, SpringJdbcTransaction> {
    private lateinit var context: GenericApplicationContext

    @BeforeAll
    fun beforeAll(containerResource: TestContainersCloseableResource) {
        val dataSource = MysqlDataSource()
        dataSource.setURL(
            "jdbc:mysql://${containerResource.host}:${containerResource.firstMappedPort}/db"
        )
        dataSource.user = "mysql"
        dataSource.password = "test"
        val beans = beans {
            bean {
                JdbcTemplate(dataSource)
            }
            bean {
                DataSourceTransactionManager(dataSource)
            }
        }
        context = GenericApplicationContext().apply {
            beans.initialize(this)
            refresh()
        }
        repository.init()
    }

    protected abstract fun instantiateRepository(jdbcOperations: JdbcOperations): T

    override val repository: T by lazy {
        instantiateRepository(context.getBean())
    }

    override val operator: SpringJdbcTransactionalOp by lazy {
        val transactionManager = context.getBean<PlatformTransactionManager>()
        TransactionTemplate(transactionManager).transactionalOp()
    }

    @AfterAll
    fun afterAll() {
        repository.delete()
        context.close()
    }
}
