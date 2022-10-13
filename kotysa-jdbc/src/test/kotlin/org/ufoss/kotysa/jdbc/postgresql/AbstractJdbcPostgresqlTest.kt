/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.postgresql

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.parallel.ResourceLock
import org.postgresql.ds.PGSimpleDataSource
import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.jdbc.sqlClient
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.hooks.*
import org.ufoss.kotysa.test.postgresqlTables
import org.ufoss.kotysa.test.repositories.blocking.RepositoryTest

@ExtendWith(PostgreSqlContainerExecutionHook::class)
@ResourceLock(PostgreSqlContainerResource.ID)
@Tag("jdbc-testcontainers")
abstract class AbstractJdbcPostgresqlTest<T : Repository> : RepositoryTest<T, JdbcTransaction> {
    private lateinit var sqlClient: JdbcSqlClient

    @BeforeAll
    fun beforeAll(containerResource: TestContainersCloseableResource) {
        val dataSource = PGSimpleDataSource()
        dataSource.setUrl(
            "jdbc:postgresql://${containerResource.host}:${containerResource.firstMappedPort}/db"
        )
        dataSource.user = "postgres"
        dataSource.password = "test"
        sqlClient = dataSource.sqlClient(postgresqlTables)
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
