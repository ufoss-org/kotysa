/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.oracle

import io.vertx.kotlin.coroutines.await
import io.vertx.oracleclient.OracleConnectOptions
import io.vertx.oracleclient.OraclePool
import io.vertx.sqlclient.Pool
import io.vertx.sqlclient.PoolOptions
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.parallel.ResourceLock
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.hooks.OracleContainerExecutionHook
import org.ufoss.kotysa.test.hooks.OracleContainerResource
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.oracleTables
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesRepositoryTest
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.vertx.coSqlClient

@ExtendWith(OracleContainerExecutionHook::class)
@ResourceLock(OracleContainerResource.ID)
abstract class AbstractVertxCoroutinesOracleTest<T : Repository> : CoroutinesRepositoryTest<T, Transaction> {
    private lateinit var sqlClient: CoroutinesVertxSqlClient
    private lateinit var pool: Pool

    @BeforeAll
    fun beforeAll(containerResource: TestContainersCloseableResource) {
        val clientOptions = OracleConnectOptions
            .fromUri("oracle:thin:@${containerResource.host}:${containerResource.firstMappedPort}/db")
            .setUser("test")
            .setPassword("test")
        pool = OraclePool.pool(clientOptions, PoolOptions())

        sqlClient = pool.coSqlClient(oracleTables)
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
