/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.oracle

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.OracleCompanies
import org.ufoss.kotysa.test.OracleRoles
import org.ufoss.kotysa.test.OracleUserRoles
import org.ufoss.kotysa.test.OracleUsers
import org.ufoss.kotysa.test.repositories.blocking.SelectBooleanRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectBooleanTest

class JdbcSelectBooleanOracleTest : AbstractJdbcOracleTest<UserRepositoryJdbcOracleSelectBoolean>(),
    SelectBooleanTest<OracleRoles, OracleUsers, OracleUserRoles, OracleCompanies, UserRepositoryJdbcOracleSelectBoolean,
            JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = UserRepositoryJdbcOracleSelectBoolean(sqlClient)
}

class UserRepositoryJdbcOracleSelectBoolean(sqlClient: JdbcSqlClient) :
    SelectBooleanRepository<OracleRoles, OracleUsers, OracleUserRoles, OracleCompanies>(
        sqlClient,
        OracleRoles,
        OracleUsers,
        OracleUserRoles,
        OracleCompanies
    )
