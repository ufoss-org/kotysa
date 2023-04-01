/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mssql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MssqlRoles
import org.ufoss.kotysa.test.MssqlUserRoles
import org.ufoss.kotysa.test.MssqlUsers
import org.ufoss.kotysa.test.repositories.blocking.SelectStringRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectStringTest

class JdbcSelectStringMssqlTest : AbstractJdbcMssqlTest<UserRepositoryJdbcMssqlSelectString>(),
    SelectStringTest<MssqlRoles, MssqlUsers, MssqlUserRoles, UserRepositoryJdbcMssqlSelectString, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = UserRepositoryJdbcMssqlSelectString(sqlClient)
}

class UserRepositoryJdbcMssqlSelectString(sqlClient: JdbcSqlClient) :
    SelectStringRepository<MssqlRoles, MssqlUsers, MssqlUserRoles>(sqlClient, MssqlRoles, MssqlUsers, MssqlUserRoles)
