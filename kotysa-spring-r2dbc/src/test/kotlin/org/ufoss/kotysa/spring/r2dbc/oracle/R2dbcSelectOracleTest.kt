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
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectTest

class JdbcSelectOracleTest : AbstractR2dbcOracleTest<UserRepositoryJdbcOracleSelect>(),
    ReactorSelectTest<OracleRoles, OracleUsers, OracleUserRoles, UserRepositoryJdbcOracleSelect, ReactorTransaction> {
    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<UserRepositoryJdbcOracleSelect>(resource)
    }

    override val repository: UserRepositoryJdbcOracleSelect by lazy {
        getContextRepository()
    }
}

class UserRepositoryJdbcOracleSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectRepository<OracleRoles, OracleUsers, OracleUserRoles>(
        sqlClient,
        OracleRoles,
        OracleUsers,
        OracleUserRoles
    )
