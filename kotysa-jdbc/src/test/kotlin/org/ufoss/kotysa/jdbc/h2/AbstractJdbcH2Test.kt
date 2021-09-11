/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.ufoss.kotysa.jdbc.transaction.transactionalOp
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.repositories.JdbcRepositoryTest
import org.ufoss.kotysa.transaction.TransactionalOp
import java.sql.Connection
import java.sql.DriverManager

abstract class AbstractJdbcH2Test<T : Repository> : JdbcRepositoryTest<T> {
    private lateinit var connection: Connection

    @BeforeAll
    fun beforeAll() {
        connection = DriverManager.getConnection("jdbc:h2:mem:///testdb;DB_CLOSE_DELAY=-1")
        repository.init()
    }

    protected abstract fun instantiateRepository(connection: Connection): T

    override val operator: TransactionalOp by lazy {
        connection.transactionalOp()
    }

    override val repository: T by lazy {
        instantiateRepository(connection)
    }

    @AfterAll
    fun afterAll() {
        repository.delete()
        connection.close()
    }
}
