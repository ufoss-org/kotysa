/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.oracle

import org.ufoss.kotysa.OracleCoroutinesSqlClient
import org.ufoss.kotysa.OracleReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.OracleRoles
import org.ufoss.kotysa.test.OracleUserRoles
import org.ufoss.kotysa.test.OracleUsers
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectDistinctRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectDistinctTest

class R2dbcSelectDistinctOracleTest : AbstractR2dbcOracleTest<UserRepositoryJdbcOracleSelectDistinct>(),
    ReactorSelectDistinctTest<OracleRoles, OracleUsers, OracleUserRoles, UserRepositoryJdbcOracleSelectDistinct,
            ReactorTransaction> {
    override fun instantiateRepository(sqlClient: OracleReactorSqlClient, coSqlClient: OracleCoroutinesSqlClient) =
        UserRepositoryJdbcOracleSelectDistinct(sqlClient)
}

class UserRepositoryJdbcOracleSelectDistinct(sqlClient: ReactorSqlClient) :
    ReactorSelectDistinctRepository<OracleRoles, OracleUsers, OracleUserRoles>(
        sqlClient,
        OracleRoles,
        OracleUsers,
        OracleUserRoles
    )
