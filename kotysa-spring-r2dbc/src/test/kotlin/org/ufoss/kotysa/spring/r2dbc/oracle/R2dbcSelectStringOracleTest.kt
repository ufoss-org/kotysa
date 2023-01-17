/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.oracle

import org.junit.jupiter.api.BeforeAll
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.OracleRoles
import org.ufoss.kotysa.test.OracleUserRoles
import org.ufoss.kotysa.test.OracleUsers
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectStringRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectStringTest

class JdbcSelectStringOracleTest : AbstractR2dbcOracleTest<UserRepositoryJdbcOracleSelectString>(),
    ReactorSelectStringTest<OracleRoles, OracleUsers, OracleUserRoles, UserRepositoryJdbcOracleSelectString,
            ReactorTransaction> {
    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<UserRepositoryJdbcOracleSelectString>(resource)
    }

    override val repository: UserRepositoryJdbcOracleSelectString by lazy {
        getContextRepository()
    }
}

class UserRepositoryJdbcOracleSelectString(sqlClient: ReactorSqlClient) :
    ReactorSelectStringRepository<OracleRoles, OracleUsers, OracleUserRoles>(
        sqlClient,
        OracleRoles,
        OracleUsers,
        OracleUserRoles
    )
