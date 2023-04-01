/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.mysql

import io.vertx.mutiny.mysqlclient.MySQLPool
import io.vertx.mutiny.sqlclient.Pool
import io.vertx.sqlclient.SqlConnectOptions
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.parallel.ResourceLock
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.hooks.*
import org.ufoss.kotysa.test.mysqlTables
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinyRepositoryTest
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.sqlClient

@ExtendWith(MySqlContainerExecutionHook::class)
@ResourceLock(MySqlContainerResource.ID)
abstract class AbstractVertxSqlClientMysqlTest<T : Repository> : MutinyRepositoryTest<T> {
    private lateinit var sqlClient: VertxSqlClient
    private lateinit var pool: Pool

    @BeforeAll
    fun beforeAll(containerResource: TestContainersCloseableResource) {
        val clientOptions = SqlConnectOptions
            .fromUri("mysql://${containerResource.host}:${containerResource.firstMappedPort}/db")
            .setUser("mysql")
            .setPassword("test")
        pool = MySQLPool.pool(clientOptions)

        sqlClient = pool.sqlClient(mysqlTables)
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
        pool.closeAndAwait()
    }
}
