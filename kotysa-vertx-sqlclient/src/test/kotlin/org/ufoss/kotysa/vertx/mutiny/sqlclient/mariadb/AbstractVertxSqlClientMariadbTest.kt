/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.mariadb

import io.vertx.mutiny.mysqlclient.MySQLPool
import io.vertx.sqlclient.SqlConnectOptions
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.parallel.ResourceLock
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.hooks.*
import org.ufoss.kotysa.test.mariadbTables
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxRepositoryTest
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.sqlClient

@ExtendWith(MariadbContainerExecutionHook::class)
@ResourceLock(MariadbContainerResource.ID)
abstract class AbstractVertxSqlClientMariadbTest<T : Repository> : VertxRepositoryTest<T> {
    private lateinit var sqlClient: VertxSqlClient

    @BeforeAll
    fun beforeAll(containerResource: TestContainersCloseableResource) {
        val clientOptions = SqlConnectOptions
            .fromUri("mariadb://${containerResource.host}:${containerResource.firstMappedPort}/db")
            .setUser("mariadb")
            .setPassword("test")
        val client = MySQLPool.pool(clientOptions)

        sqlClient = client.sqlClient(mariadbTables)
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
