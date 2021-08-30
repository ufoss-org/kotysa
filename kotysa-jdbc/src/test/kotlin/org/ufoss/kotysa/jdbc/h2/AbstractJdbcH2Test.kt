/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.ufoss.kotysa.jdbc.transaction.transactionalOp
import org.ufoss.kotysa.test.Repository
import java.sql.Connection
import java.sql.DriverManager

abstract class AbstractJdbcH2Test<T : Repository> {
    private val connection = DriverManager.getConnection("jdbc:h2:mem:test")
    protected lateinit var repository: T
    protected val operator = connection.transactionalOp()

    @BeforeAll
    fun beforeAll() {
        repository = instanciateRepository(connection)
        repository.init()
    }

    protected abstract fun instanciateRepository(connection: Connection): T

    @AfterAll
    fun afterAll() {
        repository.delete()
        connection.close()
    }
}
