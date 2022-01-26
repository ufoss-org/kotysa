/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mariadb

import org.junit.jupiter.api.BeforeAll
import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.spring.r2dbc.R2dbcJavaEntityTest
import org.ufoss.kotysa.spring.r2dbc.R2dbcJavaUserRepository
import org.ufoss.kotysa.spring.r2dbc.sqlClient
import org.ufoss.kotysa.test.MARIADB_JAVA_USER
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mariadbTables


class R2DbcJavaEntityMysqlTest :
        AbstractR2dbcMariadbTest<JavaUserMariadbRepository>(),
    R2dbcJavaEntityTest<MARIADB_JAVA_USER, JavaUserMariadbRepository> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<JavaUserMariadbRepository>(resource)
    }

    override val repository: JavaUserMariadbRepository by lazy {
        getContextRepository()
    }
}


class JavaUserMariadbRepository(client: DatabaseClient)
    : R2dbcJavaUserRepository<MARIADB_JAVA_USER>(client.sqlClient(mariadbTables), MARIADB_JAVA_USER)
