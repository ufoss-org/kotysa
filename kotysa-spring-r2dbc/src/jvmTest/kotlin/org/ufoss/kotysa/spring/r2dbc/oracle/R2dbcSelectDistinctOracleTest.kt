/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.oracle

import org.ufoss.kotysa.OracleCoroutinesSqlClient
import org.ufoss.kotysa.OracleReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.OracleCompanies
import org.ufoss.kotysa.test.OracleRoles
import org.ufoss.kotysa.test.OracleUserRoles
import org.ufoss.kotysa.test.OracleUsers
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectDistinctRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectDistinctTest

class R2dbcSelectDistinctOracleTest : AbstractR2dbcOracleTest<UserRepositoryR2dbcOracleSelectDistinct>(),
    ReactorSelectDistinctTest<OracleRoles, OracleUsers, OracleUserRoles, OracleCompanies,
            UserRepositoryR2dbcOracleSelectDistinct, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: OracleReactorSqlClient, coSqlClient: OracleCoroutinesSqlClient) =
        UserRepositoryR2dbcOracleSelectDistinct(sqlClient)
}

class UserRepositoryR2dbcOracleSelectDistinct(sqlClient: ReactorSqlClient) :
    ReactorSelectDistinctRepository<OracleRoles, OracleUsers, OracleUserRoles, OracleCompanies>(
        sqlClient,
        OracleRoles,
        OracleUsers,
        OracleUserRoles,
        OracleCompanies
    )
