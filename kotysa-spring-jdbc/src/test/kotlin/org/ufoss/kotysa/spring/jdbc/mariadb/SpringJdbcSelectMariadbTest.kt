/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mariadb

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MariadbRoles
import org.ufoss.kotysa.test.MariadbUserRoles
import org.ufoss.kotysa.test.MariadbUsers
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mariadbTables
import org.ufoss.kotysa.test.repositories.blocking.SelectRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectTest

class SpringJdbcSelectMariadbTest : AbstractSpringJdbcMariadbTest<UserRepositorySpringJdbcMariadbSelect>(),
    SelectTest<MariadbRoles, MariadbUsers, MariadbUserRoles, UserRepositorySpringJdbcMariadbSelect,
            SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<UserRepositorySpringJdbcMariadbSelect>(resource)
    }

    override val repository: UserRepositorySpringJdbcMariadbSelect by lazy {
        getContextRepository()
    }
}

class UserRepositorySpringJdbcMariadbSelect(client: JdbcOperations) :
    SelectRepository<MariadbRoles, MariadbUsers, MariadbUserRoles>(
        client.sqlClient(mariadbTables),
        MariadbRoles,
        MariadbUsers,
        MariadbUserRoles
    )
