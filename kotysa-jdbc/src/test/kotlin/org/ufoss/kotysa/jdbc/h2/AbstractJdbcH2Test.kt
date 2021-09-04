/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.ufoss.kotysa.jdbc.JdbcRepositoryTest
import org.ufoss.kotysa.test.Repository
import java.sql.Connection
import java.sql.DriverManager

abstract class AbstractJdbcH2Test<T : Repository> : JdbcRepositoryTest<T> {
    final override lateinit var connection: Connection
    final override lateinit var repository: T

    @BeforeAll
    fun beforeAll() {
        connection = DriverManager.getConnection("jdbc:h2:mem:///testdb;DB_CLOSE_DELAY=-1")
        repository = instantiateRepository(connection)
        repository.init()
    }

    protected abstract fun instantiateRepository(connection: Connection): T

    @AfterAll
    fun afterAll() {
        repository.delete()
        connection.close()
    }
}
