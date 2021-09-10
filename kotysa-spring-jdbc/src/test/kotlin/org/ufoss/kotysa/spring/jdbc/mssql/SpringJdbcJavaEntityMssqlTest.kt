/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mssql

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.test.MSSQL_JAVA_USER
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mssqlTables
import org.ufoss.kotysa.test.repositories.JdbcJavaEntityTest
import org.ufoss.kotysa.test.repositories.JavaUserRepository


class SpringJdbcJavaEntityMssqlTest :
        AbstractSpringJdbcMssqlTest<JavaUserMssqlRepository>(), JdbcJavaEntityTest<MSSQL_JAVA_USER, JavaUserMssqlRepository> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<JavaUserMssqlRepository>(resource)
    }

    override val repository: JavaUserMssqlRepository by lazy {
        getContextRepository()
    }
}


class JavaUserMssqlRepository(client: JdbcOperations)
    : JavaUserRepository<MSSQL_JAVA_USER>(client.sqlClient(mssqlTables), MSSQL_JAVA_USER)
