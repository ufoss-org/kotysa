/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mssql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MssqlRoles
import org.ufoss.kotysa.test.MssqlUserRoles
import org.ufoss.kotysa.test.MssqlUsers
import org.ufoss.kotysa.test.repositories.blocking.SelectAndRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectAndTest

class JdbcSelectAndMssqlTest : AbstractJdbcMssqlTest<UserRepositoryJdbcMssqlSelectAnd>(),
    SelectAndTest<MssqlRoles, MssqlUsers, MssqlUserRoles, UserRepositoryJdbcMssqlSelectAnd, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = UserRepositoryJdbcMssqlSelectAnd(sqlClient)
}

class UserRepositoryJdbcMssqlSelectAnd(sqlClient: JdbcSqlClient) :
    SelectAndRepository<MssqlRoles, MssqlUsers, MssqlUserRoles>(sqlClient, MssqlRoles, MssqlUsers, MssqlUserRoles)
