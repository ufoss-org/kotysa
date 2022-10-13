/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.mssql

import io.vertx.mutiny.mssqlclient.MSSQLPool
import io.vertx.sqlclient.SqlConnectOptions
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.parallel.ResourceLock
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.hooks.*
import org.ufoss.kotysa.test.mssqlTables
import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinyRepositoryTest
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.sqlClient

@ExtendWith(MsSqlContainerExecutionHook::class)
@ResourceLock(MsSqlContainerResource.ID)
abstract class AbstractVertxSqlClientMssqlTest<T : Repository> : MutinyRepositoryTest<T> {
    private lateinit var sqlClient: VertxSqlClient

    @BeforeAll
    fun beforeAll(containerResource: TestContainersCloseableResource) {
        val clientOptions = SqlConnectOptions
            .fromUri("sqlserver://${containerResource.host}:${containerResource.firstMappedPort}")
            .setUser("SA")
            .setPassword("A_Str0ng_Required_Password")
        val client = MSSQLPool.pool(clientOptions)

        sqlClient = client.sqlClient(mssqlTables)
        repository.init()
    }

    protected abstract fun instantiateRepository(sqlClient: VertxSqlClient): T

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
