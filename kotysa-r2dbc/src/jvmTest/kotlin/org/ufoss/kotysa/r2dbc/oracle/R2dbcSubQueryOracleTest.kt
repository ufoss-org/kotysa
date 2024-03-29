/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.oracle

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.OracleCompanies
import org.ufoss.kotysa.test.OracleRoles
import org.ufoss.kotysa.test.OracleUserRoles
import org.ufoss.kotysa.test.OracleUsers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSubQueryRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSubQueryTest

class R2dbcSubQueryOracleTest : AbstractR2dbcOracleTest<UserRepositoryR2dbcOracleSubQuery>(),
    CoroutinesSubQueryTest<OracleRoles, OracleUsers, OracleUserRoles, OracleCompanies,
            UserRepositoryR2dbcOracleSubQuery, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = UserRepositoryR2dbcOracleSubQuery(sqlClient)
}

class UserRepositoryR2dbcOracleSubQuery(sqlClient: R2dbcSqlClient) :
    CoroutinesSubQueryRepository<OracleRoles, OracleUsers, OracleUserRoles, OracleCompanies>(
        sqlClient,
        OracleRoles,
        OracleUsers,
        OracleUserRoles,
        OracleCompanies
    )
