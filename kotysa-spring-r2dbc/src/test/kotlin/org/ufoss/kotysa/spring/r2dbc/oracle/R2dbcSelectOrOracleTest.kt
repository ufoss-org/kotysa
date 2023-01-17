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
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectOrRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectOrTest

class JdbcSelectOrOracleTest : AbstractR2dbcOracleTest<UserRepositoryJdbcOracleSelectOr>(),
    ReactorSelectOrTest<OracleRoles, OracleUsers, OracleUserRoles, UserRepositoryJdbcOracleSelectOr,
            ReactorTransaction> {
    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<UserRepositoryJdbcOracleSelectOr>(resource)
    }

    override val repository: UserRepositoryJdbcOracleSelectOr by lazy {
        getContextRepository()
    }
}

class UserRepositoryJdbcOracleSelectOr(sqlClient: ReactorSqlClient) :
    ReactorSelectOrRepository<OracleRoles, OracleUsers, OracleUserRoles>(
        sqlClient,
        OracleRoles,
        OracleUsers,
        OracleUserRoles
    )
