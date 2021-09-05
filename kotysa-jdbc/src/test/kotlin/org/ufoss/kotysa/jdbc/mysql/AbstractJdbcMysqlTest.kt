/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mysql

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.parallel.ResourceLock
import org.ufoss.kotysa.jdbc.JdbcRepositoryTest
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.hooks.MySqlContainerExecutionHook
import org.ufoss.kotysa.test.hooks.MySqlContainerResource
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import java.sql.Connection
import java.sql.DriverManager


@ExtendWith(MySqlContainerExecutionHook::class)
@ResourceLock(MySqlContainerResource.ID)
@Tag("jdbc-testcontainers")
abstract class AbstractJdbcMysqlTest<T : Repository> : JdbcRepositoryTest<T> {

    final override lateinit var connection: Connection
    final override lateinit var repository: T

    @BeforeAll
    fun beforeAll(containerResource: TestContainersCloseableResource) {
        connection = DriverManager.getConnection(
            "jdbc:mysql://${containerResource.containerIpAddress}:${containerResource.firstMappedPort}/db?disableMariaDbDriver",
                    "mysql",
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
