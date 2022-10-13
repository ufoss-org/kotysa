/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mssql

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MssqlJavaUsers
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mssqlTables
import org.ufoss.kotysa.test.repositories.blocking.JavaEntityTest
import org.ufoss.kotysa.test.repositories.blocking.JavaUserRepository


class SpringJdbcJavaEntityMssqlTest :
    AbstractSpringJdbcMssqlTest<JavaUserMssqlRepository>(),
    JavaEntityTest<MssqlJavaUsers, JavaUserMssqlRepository, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<JavaUserMssqlRepository>(resource)
    }

    override val repository: JavaUserMssqlRepository by lazy {
        getContextRepository()
    }
}


class JavaUserMssqlRepository(client: JdbcOperations) :
    JavaUserRepository<MssqlJavaUsers>(client.sqlClient(mssqlTables), MssqlJavaUsers)
