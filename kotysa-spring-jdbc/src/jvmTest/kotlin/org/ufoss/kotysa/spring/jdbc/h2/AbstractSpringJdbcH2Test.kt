/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.h2.jdbcx.JdbcDataSource
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
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
import org.ufoss.kotysa.test.repositories.blocking.RepositoryTest


abstract class AbstractSpringJdbcH2Test<T : Repository> : RepositoryTest<T, SpringJdbcTransaction> {
    private lateinit var context: GenericApplicationContext
    
    @BeforeAll
    fun beforeAll() {
        val dataSource = JdbcDataSource()
        dataSource.setUrl("jdbc:h2:mem:///testdb;DB_CLOSE_DELAY=-1")
        dataSource.user = "sa"
        dataSource.password = "sa"
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
