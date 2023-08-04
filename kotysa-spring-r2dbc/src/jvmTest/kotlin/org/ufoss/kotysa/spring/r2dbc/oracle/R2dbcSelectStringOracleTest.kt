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
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectStringRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectStringTest

class R2dbcSelectStringOracleTest : AbstractR2dbcOracleTest<UserRepositoryR2dbcOracleSelectString>(),
    ReactorSelectStringTest<OracleRoles, OracleUsers, OracleUserRoles, UserRepositoryR2dbcOracleSelectString,
            ReactorTransaction> {
    override fun instantiateRepository(sqlClient: OracleReactorSqlClient, coSqlClient: OracleCoroutinesSqlClient) =
        UserRepositoryR2dbcOracleSelectString(sqlClient)
}

class UserRepositoryR2dbcOracleSelectString(sqlClient: ReactorSqlClient) :
    ReactorSelectStringRepository<OracleRoles, OracleUsers, OracleUserRoles>(
        sqlClient,
        OracleRoles,
        OracleUsers,
        OracleUserRoles
    )
