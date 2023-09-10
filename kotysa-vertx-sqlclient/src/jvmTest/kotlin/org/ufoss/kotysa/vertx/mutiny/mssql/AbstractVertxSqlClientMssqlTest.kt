/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mssql

import io.vertx.mutiny.mssqlclient.MSSQLPool
import io.vertx.mutiny.sqlclient.Pool
import io.vertx.sqlclient.SqlConnectOptions
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.parallel.ResourceLock
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.hooks.*
import org.ufoss.kotysa.test.mssqlTables
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinyRepositoryTest
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.sqlClient

@ExtendWith(MsSqlContainerExecutionHook::class)
@ResourceLock(MsSqlContainerResource.ID)
abstract class AbstractVertxSqlClientMssqlTest<T : Repository> : MutinyRepositoryTest<T> {
    private lateinit var sqlClient: MutinyVertxSqlClient
    private lateinit var pool: Pool

    @BeforeAll
    fun beforeAll(containerResource: TestContainersCloseableResource) {
        val clientOptions = SqlConnectOptions
            .fromUri("sqlserver://${containerResource.host}:${containerResource.firstMappedPort}")
            .setUser("SA")
            .setPassword("A_Str0ng_Required_Password")
        pool = MSSQLPool.pool(clientOptions)

        sqlClient = pool.sqlClient(mssqlTables)
        repository.init()
    }

    protected abstract fun instantiateRepository(sqlClient: MutinyVertxSqlClient): T

    override val operator by lazy {
        sqlClient
    }

    override val repository: T by lazy {
        instantiateRepository(sqlClient)
    }

    @AfterAll
    fun afterAll() {
        repository.delete()
        pool.closeAndAwait()
    }
}
