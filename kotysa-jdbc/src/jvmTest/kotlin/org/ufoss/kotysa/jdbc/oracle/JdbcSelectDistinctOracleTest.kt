/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.oracle

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.OracleRoles
import org.ufoss.kotysa.test.OracleUserRoles
import org.ufoss.kotysa.test.OracleUsers
import org.ufoss.kotysa.test.repositories.blocking.SelectDistinctRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectDistinctTest

class JdbcSelectDistinctOracleTest : AbstractJdbcOracleTest<UserRepositoryJdbcOracleSelectDistinct>(),
    SelectDistinctTest<OracleRoles, OracleUsers, OracleUserRoles, UserRepositoryJdbcOracleSelectDistinct, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = UserRepositoryJdbcOracleSelectDistinct(sqlClient)
}

class UserRepositoryJdbcOracleSelectDistinct(sqlClient: JdbcSqlClient) :
    SelectDistinctRepository<OracleRoles, OracleUsers, OracleUserRoles>(sqlClient, OracleRoles, OracleUsers, OracleUserRoles)
