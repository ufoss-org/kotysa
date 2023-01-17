/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.oracle

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.OracleRoles
import org.ufoss.kotysa.test.OracleUserRoles
import org.ufoss.kotysa.test.OracleUsers
import org.ufoss.kotysa.test.repositories.blocking.SelectRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectTest

class JdbcSelectOracleTest : AbstractJdbcOracleTest<UserRepositoryJdbcOracleSelect>(),
    SelectTest<OracleRoles, OracleUsers, OracleUserRoles, UserRepositoryJdbcOracleSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = UserRepositoryJdbcOracleSelect(sqlClient)
}

class UserRepositoryJdbcOracleSelect(sqlClient: JdbcSqlClient) :
    SelectRepository<OracleRoles, OracleUsers, OracleUserRoles>(sqlClient, OracleRoles, OracleUsers, OracleUserRoles)
