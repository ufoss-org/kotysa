/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mssql

import com.microsoft.sqlserver.jdbc.SQLServerDataSource
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.parallel.ResourceLock
import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.jdbc.sqlClient
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.hooks.*
import org.ufoss.kotysa.test.mssqlTables
import org.ufoss.kotysa.test.repositories.RepositoryTest

@ExtendWith(MsSqlContainerExecutionHook::class)
@ResourceLock(MsSqlContainerResource.ID)
@Tag("jdbc-testcontainers")
abstract class AbstractJdbcMssqlTest<T : Repository> : RepositoryTest<T, JdbcTransaction> {
    private lateinit var sqlClient: JdbcSqlClient

    @BeforeAll
    fun beforeAll(containerResource: TestContainersCloseableResource) {
        val dataSource = SQLServerDataSource()
        dataSource.url = "jdbc:sqlserver://${containerResource.host}:${containerResource.firstMappedPort}"
        dataSource.user = "SA"
        dataSource.setPassword("A_Str0ng_Required_Password")
        dataSource.encrypt = java.lang.Boolean.toString(false)
        sqlClient = dataSource.sqlClient(mssqlTables)
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
