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
import org.ufoss.kotysa.test.repositories.blocking.SelectRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectTest

class SpringJdbcSelectMssqlTest : AbstractSpringJdbcMssqlTest<UserRepositorySpringJdbcMssqlSelect>(),
    SelectTest<MssqlRoles, MssqlUsers, MssqlUserRoles, UserRepositorySpringJdbcMssqlSelect, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<UserRepositorySpringJdbcMssqlSelect>(resource)
    }

    override val repository: UserRepositorySpringJdbcMssqlSelect by lazy {
        getContextRepository()
    }
}

class UserRepositorySpringJdbcMssqlSelect(client: JdbcOperations) :
    SelectRepository<MssqlRoles, MssqlUsers, MssqlUserRoles>(
        client.sqlClient(mssqlTables),
        MssqlRoles,
        MssqlUsers,
        MssqlUserRoles
    )
