/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import org.junit.jupiter.api.BeforeAll
import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.r2dbc.R2dbcJavaEntityTest
import org.ufoss.kotysa.r2dbc.R2dbcJavaUserRepository
import org.ufoss.kotysa.r2dbc.sqlClient
import org.ufoss.kotysa.test.POSTGRESQL_JAVA_USER
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.postgresqlTables


class R2DbcJavaEntityPostgresqlTest : AbstractR2dbcPostgresqlTest<JavaUserPostgresqlRepository>(),
        R2dbcJavaEntityTest<POSTGRESQL_JAVA_USER, JavaUserPostgresqlRepository> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<JavaUserPostgresqlRepository>(resource)
    }

    override val repository: JavaUserPostgresqlRepository by lazy {
        getContextRepository()
    }
}


class JavaUserPostgresqlRepository(client: DatabaseClient)
    : R2dbcJavaUserRepository<POSTGRESQL_JAVA_USER>(client.sqlClient(postgresqlTables), POSTGRESQL_JAVA_USER)
