/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.postgresql

import io.vertx.mutiny.pgclient.PgPool
import io.vertx.mutiny.sqlclient.Pool
import io.vertx.sqlclient.SqlConnectOptions
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.parallel.ResourceLock
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.hooks.*
import org.ufoss.kotysa.test.postgresqlTables
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinyRepositoryTest
import org.ufoss.kotysa.vertx.mutiny.sqlclient.PostgresqlVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.sqlClient

@ExtendWith(PostgreSqlContainerExecutionHook::class)
@ResourceLock(PostgreSqlContainerResource.ID)
abstract class AbstractVertxSqlClientPostgresqlTest<T : Repository> : MutinyRepositoryTest<T> {
    private lateinit var sqlClient: PostgresqlVertxSqlClient
    private lateinit var pool: Pool

    @BeforeAll
    fun beforeAll(containerResource: TestContainersCloseableResource) {
        val clientOptions = SqlConnectOptions
            .fromUri("postgresql://${containerResource.host}:${containerResource.firstMappedPort}/db")
            .setUser("postgres")
            .setPassword("test")
        pool = PgPool.pool(clientOptions)

        sqlClient = pool.sqlClient(postgresqlTables)
        repository.init()
    }

    protected abstract fun instantiateRepository(sqlClient: PostgresqlVertxSqlClient): T

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
