/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.oracle

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.OracleRoles
import org.ufoss.kotysa.test.OracleUserRoles
import org.ufoss.kotysa.test.OracleUsers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectOrRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectOrTest

class R2dbcSelectOrOracleTest : AbstractR2dbcOracleTest<UserRepositoryJdbcOracleSelectOr>(),
    CoroutinesSelectOrTest<OracleRoles, OracleUsers, OracleUserRoles, UserRepositoryJdbcOracleSelectOr,
            R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = UserRepositoryJdbcOracleSelectOr(sqlClient)
}

class UserRepositoryJdbcOracleSelectOr(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectOrRepository<OracleRoles, OracleUsers, OracleUserRoles>(
        sqlClient,
        OracleRoles,
        OracleUsers,
        OracleUserRoles
    )
