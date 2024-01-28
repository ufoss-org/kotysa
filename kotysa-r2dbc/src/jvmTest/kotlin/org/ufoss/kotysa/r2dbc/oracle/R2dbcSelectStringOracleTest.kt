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
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectStringRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectStringTest


class R2dbcSelectStringOracleTest : AbstractR2dbcOracleTest<UserRepositoryJdbcOracleSelectString>(),
    CoroutinesSelectStringTest<OracleRoles, OracleUsers, OracleUserRoles, OracleCompanies,
            UserRepositoryJdbcOracleSelectString, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = UserRepositoryJdbcOracleSelectString(sqlClient)
}

class UserRepositoryJdbcOracleSelectString(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectStringRepository<OracleRoles, OracleUsers, OracleUserRoles, OracleCompanies>(
        sqlClient,
        OracleRoles,
        OracleUsers,
        OracleUserRoles,
        OracleCompanies
    )
