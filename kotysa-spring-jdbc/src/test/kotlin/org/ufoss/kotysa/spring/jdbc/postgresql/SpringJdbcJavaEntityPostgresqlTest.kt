/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.PostgresqlJavaUsers
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.postgresqlTables
import org.ufoss.kotysa.test.repositories.blocking.JavaEntityTest
import org.ufoss.kotysa.test.repositories.blocking.JavaUserRepository


class SpringJdbcJavaEntityPostgresqlTest :
    AbstractSpringJdbcPostgresqlTest<JavaUserPostgresqlRepository>(),
    JavaEntityTest<PostgresqlJavaUsers, JavaUserPostgresqlRepository, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<JavaUserPostgresqlRepository>(resource)
    }

    override val repository: JavaUserPostgresqlRepository by lazy {
        getContextRepository()
    }
}


class JavaUserPostgresqlRepository(client: JdbcOperations) :
    JavaUserRepository<PostgresqlJavaUsers>(client.sqlClient(postgresqlTables), PostgresqlJavaUsers)
