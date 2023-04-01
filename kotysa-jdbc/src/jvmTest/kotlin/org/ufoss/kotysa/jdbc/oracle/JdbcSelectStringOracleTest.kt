/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.oracle

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.OracleRoles
import org.ufoss.kotysa.test.OracleUserRoles
import org.ufoss.kotysa.test.OracleUsers
import org.ufoss.kotysa.test.repositories.blocking.SelectStringRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectStringTest

class JdbcSelectStringOracleTest : AbstractJdbcOracleTest<UserRepositoryJdbcOracleSelectString>(),
    SelectStringTest<OracleRoles, OracleUsers, OracleUserRoles, UserRepositoryJdbcOracleSelectString, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = UserRepositoryJdbcOracleSelectString(sqlClient)
}

class UserRepositoryJdbcOracleSelectString(sqlClient: JdbcSqlClient) :
    SelectStringRepository<OracleRoles, OracleUsers, OracleUserRoles>(sqlClient, OracleRoles, OracleUsers, OracleUserRoles)
