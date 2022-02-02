/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mariadb

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.parallel.ResourceLock
import org.mariadb.jdbc.MariaDbDataSource
import org.ufoss.kotysa.jdbc.JdbcSqlClient
import org.ufoss.kotysa.jdbc.sqlClient
import org.ufoss.kotysa.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.hooks.*
import org.ufoss.kotysa.test.mariadbTables
import org.ufoss.kotysa.test.repositories.RepositoryTest

@ExtendWith(MariadbContainerExecutionHook::class)
@ResourceLock(MariadbContainerResource.ID)
@Tag("jdbc-testcontainers")
abstract class AbstractJdbcMariadbTest<T : Repository> : RepositoryTest<T, JdbcTransaction> {
    private lateinit var sqlClient: JdbcSqlClient

    @BeforeAll
    fun beforeAll(containerResource: TestContainersCloseableResource) {
        val dataSource = MariaDbDataSource(
            "jdbc:mariadb://${containerResource.containerIpAddress}:${containerResource.firstMappedPort}/db"
        )
        dataSource.user = "mariadb"
        dataSource.setPassword("test")
        sqlClient = dataSource.sqlClient(mariadbTables)
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
