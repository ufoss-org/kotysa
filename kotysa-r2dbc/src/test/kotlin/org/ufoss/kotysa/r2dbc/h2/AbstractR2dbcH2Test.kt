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
import org.ufoss.kotysa.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.r2dbc.transaction.R2dbcTransactionalOp
import org.ufoss.kotysa.r2dbc.transaction.transactionalOp

abstract class AbstractR2dbcH2Test<T : Repository> : CoroutinesRepositoryTest<T, R2dbcTransaction> {
    private lateinit var connection: Connection

    @BeforeAll
    fun beforeAll() = runBlocking {
        connection = ConnectionFactories.get("r2dbc:h2:mem:///testdb;DB_CLOSE_DELAY=-1").create().awaitSingle()
        repository.init()
    }

    protected abstract fun instantiateRepository(connection: Connection): T

    override val operator: R2dbcTransactionalOp by lazy {
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
