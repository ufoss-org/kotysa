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
import org.ufoss.kotysa.test.repositories.blocking.SelectAndRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectAndTest

class JdbcSelectAndOracleTest : AbstractJdbcOracleTest<UserRepositoryJdbcOracleSelectAnd>(),
    SelectAndTest<OracleRoles, OracleUsers, OracleUserRoles, OracleCompanies, UserRepositoryJdbcOracleSelectAnd,
            JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = UserRepositoryJdbcOracleSelectAnd(sqlClient)
}

class UserRepositoryJdbcOracleSelectAnd(sqlClient: JdbcSqlClient) :
    SelectAndRepository<OracleRoles, OracleUsers, OracleUserRoles, OracleCompanies>(
        sqlClient,
        OracleRoles,
        OracleUsers,
        OracleUserRoles,
        OracleCompanies
    )
