/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mssql

import io.vertx.kotlin.coroutines.await
import io.vertx.mssqlclient.MSSQLConnectOptions
import io.vertx.mssqlclient.MSSQLPool
import io.vertx.sqlclient.Pool
import io.vertx.sqlclient.PoolOptions
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.parallel.ResourceLock
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.hooks.MsSqlContainerExecutionHook
import org.ufoss.kotysa.test.hooks.MsSqlContainerResource
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mssqlTables
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesRepositoryTest
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.vertx.coSqlClient

@ExtendWith(MsSqlContainerExecutionHook::class)
@ResourceLock(MsSqlContainerResource.ID)
abstract class AbstractVertxCoroutinesMssqlTest<T : Repository> : CoroutinesRepositoryTest<T, Transaction> {
    private lateinit var sqlClient: CoroutinesVertxSqlClient
    private lateinit var pool: Pool

    @BeforeAll
    fun beforeAll(containerResource: TestContainersCloseableResource) {
        val clientOptions = MSSQLConnectOptions
            .fromUri("sqlserver://${containerResource.host}:${containerResource.firstMappedPort}")
            .setUser("SA")
            .setPassword("A_Str0ng_Required_Password")
        pool = MSSQLPool.pool(clientOptions, PoolOptions())

        sqlClient = pool.coSqlClient(mssqlTables)
        repository.init()
    }

    protected abstract fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient): T

    override val coOperator by lazy {
        sqlClient
    }

    override val repository: T by lazy {
        instantiateRepository(sqlClient)
    }

    @AfterAll
    fun afterAll() = runBlocking<Unit> {
        repository.delete()
        pool.close().await()
    }
}
