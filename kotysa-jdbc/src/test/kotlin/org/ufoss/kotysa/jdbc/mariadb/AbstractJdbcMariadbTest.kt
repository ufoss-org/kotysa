/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mariadb

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.parallel.ResourceLock
import org.ufoss.kotysa.jdbc.JdbcRepositoryTest
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.hooks.*
import java.sql.Connection
import java.sql.DriverManager


@ExtendWith(MariadbContainerExecutionHook::class)
@ResourceLock(MariadbContainerResource.ID)
@Tag("jdbc-testcontainers")
abstract class AbstractJdbcMariadbTest<T : Repository> : JdbcRepositoryTest<T> {

    final override lateinit var connection: Connection
    final override lateinit var repository: T

    @BeforeAll
    fun beforeAll(containerResource: TestContainersCloseableResource) {
        connection = DriverManager.getConnection(
            "jdbc:mariadb://${containerResource.containerIpAddress}:${containerResource.firstMappedPort}/db",
            "mariadb",
            "test",
        )
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
