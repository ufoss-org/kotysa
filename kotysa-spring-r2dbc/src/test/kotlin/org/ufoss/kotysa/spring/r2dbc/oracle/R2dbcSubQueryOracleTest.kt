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
import org.ufoss.kotysa.test.repositories.reactor.ReactorSubQueryRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSubQueryTest

class JdbcSubQueryOracleTest : AbstractR2dbcOracleTest<UserRepositoryJdbcOracleSubQuery>(),
    ReactorSubQueryTest<OracleRoles, OracleUsers, OracleUserRoles, UserRepositoryJdbcOracleSubQuery,
            ReactorTransaction> {
    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<UserRepositoryJdbcOracleSubQuery>(resource)
    }

    override val repository: UserRepositoryJdbcOracleSubQuery by lazy {
        getContextRepository()
    }
}

class UserRepositoryJdbcOracleSubQuery(sqlClient: ReactorSqlClient) :
    ReactorSubQueryRepository<OracleRoles, OracleUsers, OracleUserRoles>(
        sqlClient,
        OracleRoles,
        OracleUsers,
        OracleUserRoles
    )
