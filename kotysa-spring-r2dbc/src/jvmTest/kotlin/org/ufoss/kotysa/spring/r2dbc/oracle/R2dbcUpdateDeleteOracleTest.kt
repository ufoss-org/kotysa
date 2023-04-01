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
import org.ufoss.kotysa.test.repositories.reactor.ReactorUpdateDeleteRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorUpdateDeleteTest

class JdbcUpdateDeleteOracleTest : AbstractR2dbcOracleTest<UserRepositoryR2dbcOracleUpdateDelete>(),
    ReactorUpdateDeleteTest<OracleRoles, OracleUsers, OracleUserRoles, UserRepositoryR2dbcOracleUpdateDelete,
            ReactorTransaction> {
    override fun instantiateRepository(sqlClient: OracleReactorSqlClient, coSqlClient: OracleCoroutinesSqlClient) =
        UserRepositoryR2dbcOracleUpdateDelete(sqlClient)
}

class UserRepositoryR2dbcOracleUpdateDelete(sqlClient: ReactorSqlClient) :
    ReactorUpdateDeleteRepository<OracleRoles, OracleUsers, OracleUserRoles>(
        sqlClient,
        OracleRoles,
        OracleUsers,
        OracleUserRoles
    )
