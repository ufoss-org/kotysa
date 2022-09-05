/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import org.h2.jdbcx.JdbcDataSource
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.jdbc.sqlClient
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.h2Tables
import org.ufoss.kotysa.test.repositories.RepositoryTest

abstract class AbstractJdbcH2Test<T : Repository> : RepositoryTest<T, JdbcTransaction> {
    private lateinit var sqlClient: JdbcSqlClient

    @BeforeAll
    fun beforeAll() {
        val dataSource = JdbcDataSource()
        dataSource.setUrl("jdbc:h2:mem:///testdb;DB_CLOSE_DELAY=-1")
        dataSource.user = "sa"
        dataSource.password = "sa"
        sqlClient = dataSource.sqlClient(h2Tables)
        repository.init()
    }

    protected abstract fun instantiateRepository(sqlClient: JdbcSqlClient): T

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
