/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mysql

import com.mysql.cj.jdbc.MysqlDataSource
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.parallel.ResourceLock
import org.ufoss.kotysa.jdbc.JdbcSqlClient
import org.ufoss.kotysa.jdbc.sqlClient
import org.ufoss.kotysa.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.hooks.MySqlContainerExecutionHook
import org.ufoss.kotysa.test.hooks.MySqlContainerResource
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mysqlTables
import org.ufoss.kotysa.test.repositories.RepositoryTest


@ExtendWith(MySqlContainerExecutionHook::class)
@ResourceLock(MySqlContainerResource.ID)
@Tag("jdbc-testcontainers")
abstract class AbstractJdbcMysqlTest<T : Repository> : RepositoryTest<T, JdbcTransaction> {
    private lateinit var sqlClient: JdbcSqlClient

    @BeforeAll
    fun beforeAll(containerResource: TestContainersCloseableResource) {
        val dataSource = MysqlDataSource()
        dataSource.setURL(
            "jdbc:mysql://${containerResource.host}:${containerResource.firstMappedPort}/db"
        )
        dataSource.user = "mysql"
        dataSource.password = "test"
        sqlClient = dataSource.sqlClient(mysqlTables)
        repository.init()
    }

    protected abstract fun instantiateRepository(sqlClient: JdbcSqlClient): T

    override val operator by lazy {
        sqlClient
    }

    override val repository: T by lazy {
        instantiateRepository(sqlClient)
    }

    @AfterAll
    fun afterAll() {
        repository.delete()
    }
}
