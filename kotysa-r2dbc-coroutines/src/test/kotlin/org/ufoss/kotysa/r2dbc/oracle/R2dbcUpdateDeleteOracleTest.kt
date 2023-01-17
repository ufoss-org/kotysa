/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.oracle

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.OracleRoles
import org.ufoss.kotysa.test.OracleUserRoles
import org.ufoss.kotysa.test.OracleUsers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesUpdateDeleteRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesUpdateDeleteTest

class R2dbcUpdateDeleteOracleTest : AbstractR2dbcOracleTest<UserRepositoryJdbcOracleUpdateDelete>(),
    CoroutinesUpdateDeleteTest<OracleRoles, OracleUsers, OracleUserRoles, UserRepositoryJdbcOracleUpdateDelete,
            R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = UserRepositoryJdbcOracleUpdateDelete(sqlClient)
}

class UserRepositoryJdbcOracleUpdateDelete(sqlClient: R2dbcSqlClient) :
    CoroutinesUpdateDeleteRepository<OracleRoles, OracleUsers, OracleUserRoles>(
        sqlClient,
        OracleRoles,
        OracleUsers,
        OracleUserRoles
    )
