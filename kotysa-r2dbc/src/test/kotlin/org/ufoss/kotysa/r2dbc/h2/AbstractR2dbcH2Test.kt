/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.repositories.CoroutinesRepositoryTest
import io.r2dbc.spi.Connection
import io.r2dbc.spi.ConnectionFactories
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.runBlocking
import org.ufoss.kotysa.r2dbc.transaction.transactionalOp
import org.ufoss.kotysa.transaction.CoroutinesTransactionalOp

abstract class AbstractR2dbcH2Test<T : Repository> : CoroutinesRepositoryTest<T> {
    private lateinit var connection: Connection

    @BeforeAll
    fun beforeAll() = runBlocking {
        val factory = ConnectionFactories.get("r2dbc:h2:mem:///testdb;DB_CLOSE_DELAY=-1")
        connection = factory.create().awaitSingle()
        repository.init()
    }

    protected abstract fun instantiateRepository(connection: Connection): T

    override val operator: CoroutinesTransactionalOp by lazy {
        connection.transactionalOp()
    }

    override val repository: T by lazy {
        instantiateRepository(connection)
    }

    @AfterAll
    fun afterAll() = runBlocking<Unit> {
        repository.delete()
        connection.close().awaitFirstOrNull()
    }
}
