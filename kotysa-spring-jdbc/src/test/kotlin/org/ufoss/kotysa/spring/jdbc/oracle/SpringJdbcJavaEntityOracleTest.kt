/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.oracle

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.OracleJavaUsers
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.oracleTables
import org.ufoss.kotysa.test.repositories.blocking.JavaEntityTest
import org.ufoss.kotysa.test.repositories.blocking.JavaUserRepository


class SpringJdbcJavaEntityOracleTest :
    AbstractSpringJdbcOracleTest<JavaUserOracleRepository>(),
    JavaEntityTest<OracleJavaUsers, JavaUserOracleRepository, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<JavaUserOracleRepository>(resource)
    }

    override val repository: JavaUserOracleRepository by lazy {
        getContextRepository()
    }
}


class JavaUserOracleRepository(client: JdbcOperations) :
    JavaUserRepository<OracleJavaUsers>(client.sqlClient(oracleTables), OracleJavaUsers)
