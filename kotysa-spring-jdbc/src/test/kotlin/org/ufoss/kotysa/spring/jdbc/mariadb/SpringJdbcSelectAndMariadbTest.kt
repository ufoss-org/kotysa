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
import org.ufoss.kotysa.test.repositories.blocking.SelectAndRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectAndTest

class SpringJdbcSelectAndMariadbTest : AbstractSpringJdbcMariadbTest<UserRepositorySpringJdbcMariadbSelectAnd>(),
    SelectAndTest<MariadbRoles, MariadbUsers, MariadbUserRoles, UserRepositorySpringJdbcMariadbSelectAnd,
            SpringJdbcTransaction> {
    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<UserRepositorySpringJdbcMariadbSelectAnd>(resource)
    }

    override val repository: UserRepositorySpringJdbcMariadbSelectAnd by lazy {
        getContextRepository()
    }
}

class UserRepositorySpringJdbcMariadbSelectAnd(client: JdbcOperations) :
    SelectAndRepository<MariadbRoles, MariadbUsers, MariadbUserRoles>(
        client.sqlClient(mariadbTables),
        MariadbRoles, MariadbUsers, MariadbUserRoles
    )
