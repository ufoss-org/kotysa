/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mariadb

import org.junit.jupiter.api.BeforeAll
import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.test.repositories.reactor.ReactorJavaEntityTest
import org.ufoss.kotysa.test.repositories.reactor.ReactorJavaUserRepository
import org.ufoss.kotysa.spring.r2dbc.sqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MariadbJavaUsers
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mariadbTables

class R2DbcJavaEntityMysqlTest :
        AbstractR2dbcMariadbTest<JavaUserMariadbRepository>(),
    ReactorJavaEntityTest<MariadbJavaUsers, JavaUserMariadbRepository, ReactorTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<JavaUserMariadbRepository>(resource)
    }

    override val repository: JavaUserMariadbRepository by lazy {
        getContextRepository()
    }
}


class JavaUserMariadbRepository(client: DatabaseClient)
    : ReactorJavaUserRepository<MariadbJavaUsers>(client.sqlClient(mariadbTables), MariadbJavaUsers)
