/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mssql

import org.junit.jupiter.api.BeforeAll
import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.spring.r2dbc.R2dbcJavaEntityTest
import org.ufoss.kotysa.spring.r2dbc.R2dbcJavaUserRepository
import org.ufoss.kotysa.spring.r2dbc.sqlClient
import org.ufoss.kotysa.test.MssqlJavaUsers
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mssqlTables


class R2DbcJavaEntityMssqlTest :
        AbstractR2dbcMssqlTest<JavaUserMssqlRepository>(),
    R2dbcJavaEntityTest<MssqlJavaUsers, JavaUserMssqlRepository> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<JavaUserMssqlRepository>(resource)
    }

    override val repository: JavaUserMssqlRepository by lazy {
        getContextRepository()
    }
}


class JavaUserMssqlRepository(client: DatabaseClient)
    : R2dbcJavaUserRepository<MssqlJavaUsers>(client.sqlClient(mssqlTables), MssqlJavaUsers)
