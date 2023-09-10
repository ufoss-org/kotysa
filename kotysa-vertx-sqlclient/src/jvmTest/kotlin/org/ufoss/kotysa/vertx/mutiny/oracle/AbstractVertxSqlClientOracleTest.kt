/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.oracle

import io.vertx.mutiny.oracleclient.OraclePool
import io.vertx.mutiny.sqlclient.Pool
import io.vertx.sqlclient.SqlConnectOptions
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.parallel.ResourceLock
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.hooks.*
import org.ufoss.kotysa.test.oracleTables
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinyRepositoryTest
import org.ufoss.kotysa.vertx.sqlClient

@ExtendWith(OracleContainerExecutionHook::class)
@ResourceLock(OracleContainerResource.ID)
abstract class AbstractVertxSqlClientOracleTest<T : Repository> : MutinyRepositoryTest<T> {
    private lateinit var sqlClient: MutinyVertxSqlClient
    private lateinit var pool: Pool

    @BeforeAll
    fun beforeAll(containerResource: TestContainersCloseableResource) {
        val clientOptions = SqlConnectOptions
            .fromUri("oracle:thin:@${containerResource.host}:${containerResource.firstMappedPort}/db")
            .setUser("test")
            .setPassword("test")
        pool = OraclePool.pool(clientOptions)

        sqlClient = pool.sqlClient(oracleTables)
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
