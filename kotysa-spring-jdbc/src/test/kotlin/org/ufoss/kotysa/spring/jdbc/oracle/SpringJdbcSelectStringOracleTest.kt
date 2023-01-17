/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.oracle

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.OracleRoles
import org.ufoss.kotysa.test.OracleUserRoles
import org.ufoss.kotysa.test.OracleUsers
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.oracleTables
import org.ufoss.kotysa.test.repositories.blocking.SelectStringRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectStringTest

class SpringJdbcSelectStringOracleTest : AbstractSpringJdbcOracleTest<UserRepositorySpringJdbcOracleSelectString>(),
    SelectStringTest<OracleRoles, OracleUsers, OracleUserRoles, UserRepositorySpringJdbcOracleSelectString,
            SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<UserRepositorySpringJdbcOracleSelectString>(resource)
    }

    override val repository: UserRepositorySpringJdbcOracleSelectString by lazy {
        getContextRepository()
    }
}

class UserRepositorySpringJdbcOracleSelectString(client: JdbcOperations) :
    SelectStringRepository<OracleRoles, OracleUsers, OracleUserRoles>(
        client.sqlClient(oracleTables), OracleRoles,
        OracleUsers, OracleUserRoles
    )
