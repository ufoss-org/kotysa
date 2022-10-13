/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mssql

import org.junit.jupiter.api.BeforeAll
import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.test.repositories.reactor.ReactorJavaEntityTest
import org.ufoss.kotysa.test.repositories.reactor.ReactorJavaUserRepository
import org.ufoss.kotysa.spring.r2dbc.sqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MssqlJavaUsers
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mssqlTables

class R2DbcJavaEntityMssqlTest :
        AbstractR2dbcMssqlTest<JavaUserMssqlRepository>(),
    ReactorJavaEntityTest<MssqlJavaUsers, JavaUserMssqlRepository, ReactorTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<JavaUserMssqlRepository>(resource)
    }

    override val repository: JavaUserMssqlRepository by lazy {
        getContextRepository()
    }
}


class JavaUserMssqlRepository(client: DatabaseClient)
    : ReactorJavaUserRepository<MssqlJavaUsers>(client.sqlClient(mssqlTables), MssqlJavaUsers)
