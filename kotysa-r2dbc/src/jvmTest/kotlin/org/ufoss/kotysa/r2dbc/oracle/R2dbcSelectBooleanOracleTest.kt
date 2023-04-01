/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.oracle

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.OracleRoles
import org.ufoss.kotysa.test.OracleUserRoles
import org.ufoss.kotysa.test.OracleUsers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectBooleanRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectBooleanTest


class R2dbcSelectBooleanOracleTest : AbstractR2dbcOracleTest<UserRepositoryJdbcOracleSelectBoolean>(),
    CoroutinesSelectBooleanTest<OracleRoles, OracleUsers, OracleUserRoles, UserRepositoryJdbcOracleSelectBoolean, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = UserRepositoryJdbcOracleSelectBoolean(sqlClient)
}

class UserRepositoryJdbcOracleSelectBoolean(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectBooleanRepository<OracleRoles, OracleUsers, OracleUserRoles>(sqlClient, OracleRoles, OracleUsers, OracleUserRoles)
