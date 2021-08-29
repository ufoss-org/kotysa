/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.ufoss.kotysa.test.Repository
import java.sql.Connection
import java.sql.DriverManager

abstract class AbstractJdbcH2Test<T : Repository> {
    private lateinit var connection: Connection
    protected lateinit var repository: T

    @BeforeAll
    fun beforeAll() {
        connection = DriverManager.getConnection("jdbc:h2:mem:test")
        repository = instanciateRepository(connection)
        repository.init()
    }

    protected abstract fun instanciateRepository(connection: Connection): T

    @AfterAll
    fun afterAll() {
        repository.delete()
    }
}
