/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mssql

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.parallel.ResourceLock
import org.ufoss.kotysa.jdbc.transaction.transactionalOp
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.hooks.*
import org.ufoss.kotysa.test.repositories.JdbcRepositoryTest
import org.ufoss.kotysa.transaction.TransactionalOp
import java.sql.Connection
import java.sql.DriverManager

@ExtendWith(MsSqlContainerExecutionHook::class)
@ResourceLock(MsSqlContainerResource.ID)
@Tag("jdbc-testcontainers")
abstract class AbstractJdbcMssqlTest<T : Repository> : JdbcRepositoryTest<T> {
    private lateinit var connection: Connection

    @BeforeAll
    fun beforeAll(containerResource: TestContainersCloseableResource) {
        connection = DriverManager.getConnection(
            "jdbc:sqlserver://${containerResource.containerIpAddress}:${containerResource.firstMappedPort}",
            "SA",
            "A_Str0ng_Required_Password",
        )
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
