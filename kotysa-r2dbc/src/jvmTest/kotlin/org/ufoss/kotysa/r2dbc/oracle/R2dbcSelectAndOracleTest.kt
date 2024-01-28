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
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectAndRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectAndTest


class R2dbcSelectAndOracleTest : AbstractR2dbcOracleTest<UserRepositoryJdbcOracleSelectAnd>(),
    CoroutinesSelectAndTest<OracleRoles, OracleUsers, OracleUserRoles, OracleCompanies,
            UserRepositoryJdbcOracleSelectAnd, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = UserRepositoryJdbcOracleSelectAnd(sqlClient)
}

class UserRepositoryJdbcOracleSelectAnd(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectAndRepository<OracleRoles, OracleUsers, OracleUserRoles, OracleCompanies>(
        sqlClient,
        OracleRoles,
        OracleUsers,
        OracleUserRoles,
        OracleCompanies
    )
