/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mariadb

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MariadbJavaUsers
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mariadbTables
import org.ufoss.kotysa.test.repositories.blocking.JavaEntityTest
import org.ufoss.kotysa.test.repositories.blocking.JavaUserRepository


class SpringJdbcJavaEntityMariadbTest :
    AbstractSpringJdbcMariadbTest<JavaUserMariadbRepository>(),
    JavaEntityTest<MariadbJavaUsers, JavaUserMariadbRepository, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<JavaUserMariadbRepository>(resource)
    }

    override val repository: JavaUserMariadbRepository by lazy {
        getContextRepository()
    }
}


class JavaUserMariadbRepository(client: JdbcOperations) :
    JavaUserRepository<MariadbJavaUsers>(client.sqlClient(mariadbTables), MariadbJavaUsers)
