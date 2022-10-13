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
import org.ufoss.kotysa.test.repositories.blocking.SelectStringRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectStringTest

class SpringJdbcSelectStringMariadbTest : AbstractSpringJdbcMariadbTest<UserRepositorySpringJdbcMariadbSelectString>(),
    SelectStringTest<MariadbRoles, MariadbUsers, MariadbUserRoles, UserRepositorySpringJdbcMariadbSelectString,
            SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<UserRepositorySpringJdbcMariadbSelectString>(resource)
    }

    override val repository: UserRepositorySpringJdbcMariadbSelectString by lazy {
        getContextRepository()
    }
}

class UserRepositorySpringJdbcMariadbSelectString(client: JdbcOperations) :
    SelectStringRepository<MariadbRoles, MariadbUsers, MariadbUserRoles>(
        client.sqlClient(mariadbTables), MariadbRoles,
        MariadbUsers, MariadbUserRoles
    )
