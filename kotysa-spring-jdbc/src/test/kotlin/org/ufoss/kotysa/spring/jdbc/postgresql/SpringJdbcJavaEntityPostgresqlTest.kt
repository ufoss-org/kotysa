/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.test.POSTGRESQL_JAVA_USER
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.postgresqlTables
import org.ufoss.kotysa.test.repositories.JdbcJavaEntityTest
import org.ufoss.kotysa.test.repositories.JavaUserRepository


class SpringJdbcJavaEntityPostgresqlTest : AbstractSpringJdbcPostgresqlTest<JavaUserPostgresqlRepository>(),
        JdbcJavaEntityTest<POSTGRESQL_JAVA_USER, JavaUserPostgresqlRepository> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<JavaUserPostgresqlRepository>(resource)
    }

    override val repository: JavaUserPostgresqlRepository by lazy {
        getContextRepository()
    }
}


class JavaUserPostgresqlRepository(client: JdbcOperations)
    : JavaUserRepository<POSTGRESQL_JAVA_USER>(client.sqlClient(postgresqlTables), POSTGRESQL_JAVA_USER)
