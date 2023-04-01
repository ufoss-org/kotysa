/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesRepositoryTest
import io.r2dbc.spi.ConnectionFactories
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.r2dbc.coSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.h2Tables

abstract class AbstractR2dbcH2Test<T : Repository> : CoroutinesRepositoryTest<T, R2dbcTransaction> {
    private lateinit var sqlClient: R2dbcSqlClient

    @BeforeAll
    fun beforeAll() {
        val connectionFactory = ConnectionFactories.get("r2dbc:h2:mem:///testdb;DB_CLOSE_DELAY=-1")
        sqlClient = connectionFactory.coSqlClient(h2Tables)
        repository.init()
    }

    protected abstract fun instantiateRepository(sqlClient: R2dbcSqlClient): T

    override val coOperator by lazy {
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
