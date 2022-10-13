/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mssql

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MssqlRoles
import org.ufoss.kotysa.test.MssqlUserRoles
import org.ufoss.kotysa.test.MssqlUsers
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mssqlTables
import org.ufoss.kotysa.test.repositories.blocking.UpdateDeleteRepository
import org.ufoss.kotysa.test.repositories.blocking.UpdateDeleteTest

class SpringJdbcUpdateDeleteMssqlTest : AbstractSpringJdbcMssqlTest<UserRepositorySpringJdbcMssqlUpdateDelete>(),
    UpdateDeleteTest<MssqlRoles, MssqlUsers, MssqlUserRoles, UserRepositorySpringJdbcMssqlUpdateDelete,
            SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<UserRepositorySpringJdbcMssqlUpdateDelete>(resource)
    }

    override val repository: UserRepositorySpringJdbcMssqlUpdateDelete by lazy {
        getContextRepository()
    }
}

class UserRepositorySpringJdbcMssqlUpdateDelete(client: JdbcOperations) :
    UpdateDeleteRepository<MssqlRoles, MssqlUsers, MssqlUserRoles>(
        client.sqlClient(mssqlTables),
        MssqlRoles,
        MssqlUsers,
        MssqlUserRoles
    )
