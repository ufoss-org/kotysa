/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.postgresql

import org.junit.jupiter.api.BeforeAll
import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.test.repositories.reactor.ReactorJavaEntityTest
import org.ufoss.kotysa.test.repositories.reactor.ReactorJavaUserRepository
import org.ufoss.kotysa.spring.r2dbc.sqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.PostgresqlJavaUsers
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.postgresqlTables

class R2DbcJavaEntityPostgresqlTest : AbstractR2dbcPostgresqlTest<JavaUserPostgresqlRepository>(),
    ReactorJavaEntityTest<PostgresqlJavaUsers, JavaUserPostgresqlRepository, ReactorTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<JavaUserPostgresqlRepository>(resource)
    }

    override val repository: JavaUserPostgresqlRepository by lazy {
        getContextRepository()
    }
}


class JavaUserPostgresqlRepository(client: DatabaseClient)
    : ReactorJavaUserRepository<PostgresqlJavaUsers>(client.sqlClient(postgresqlTables), PostgresqlJavaUsers)
