/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mariadb

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.test.MARIADB_JAVA_USER
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mariadbTables
import org.ufoss.kotysa.test.repositories.JavaEntityTest
import org.ufoss.kotysa.test.repositories.JavaUserRepository


class SpringJdbcJavaEntityMariadbTest :
        AbstractSpringJdbcMariadbTest<JavaUserMariadbRepository>(), JavaEntityTest<MARIADB_JAVA_USER, JavaUserMariadbRepository> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<JavaUserMariadbRepository>(resource)
        repository = getContextRepository()
    }
}


class JavaUserMariadbRepository(client: JdbcOperations)
    : JavaUserRepository<MARIADB_JAVA_USER>(client.sqlClient(mariadbTables), MARIADB_JAVA_USER)
