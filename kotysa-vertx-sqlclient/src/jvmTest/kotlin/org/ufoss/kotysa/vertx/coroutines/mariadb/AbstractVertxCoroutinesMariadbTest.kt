/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mariadb

import io.vertx.kotlin.coroutines.await
import io.vertx.mysqlclient.MySQLBuilder
import io.vertx.mysqlclient.MySQLConnectOptions
import io.vertx.sqlclient.Pool
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.parallel.ResourceLock
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.hooks.MariadbContainerExecutionHook
import org.ufoss.kotysa.test.hooks.MariadbContainerResource
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mariadbTables
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesRepositoryTest
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.vertx.coSqlClient

@ExtendWith(MariadbContainerExecutionHook::class)
@ResourceLock(MariadbContainerResource.ID)
abstract class AbstractVertxCoroutinesMariadbTest<T : Repository> : CoroutinesRepositoryTest<T, Transaction> {
    private lateinit var sqlClient: CoroutinesVertxSqlClient
    private lateinit var pool: Pool

    @BeforeAll
    fun beforeAll(containerResource: TestContainersCloseableResource) {
        val clientOptions = MySQLConnectOptions
            .fromUri("mariadb://${containerResource.host}:${containerResource.firstMappedPort}/db")
            .setUser("mariadb")
            .setPassword("test")
        pool = MySQLBuilder.pool()
            .connectingTo(clientOptions)
            .build()

        sqlClient = pool.coSqlClient(mariadbTables)
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
