/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.oracle

import org.junit.jupiter.api.BeforeAll
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.OracleJavaUsers
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.repositories.reactor.ReactorJavaEntityTest
import org.ufoss.kotysa.test.repositories.reactor.ReactorJavaUserRepository

class R2DbcJavaEntityOracleTest : AbstractR2dbcOracleTest<JavaUserOracleRepository>(),
    ReactorJavaEntityTest<OracleJavaUsers, JavaUserOracleRepository, ReactorTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<JavaUserOracleRepository>(resource)
    }

    override val repository: JavaUserOracleRepository by lazy {
        getContextRepository()
    }
}

class JavaUserOracleRepository(client: ReactorSqlClient) :
    ReactorJavaUserRepository<OracleJavaUsers>(client, OracleJavaUsers)
