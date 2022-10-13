/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mssql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MssqlRoles
import org.ufoss.kotysa.test.MssqlUserRoles
import org.ufoss.kotysa.test.MssqlUsers
import org.ufoss.kotysa.test.repositories.blocking.SelectRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectTest

class JdbcSelectMssqlTest : AbstractJdbcMssqlTest<UserRepositoryJdbcMssqlSelect>(),
    SelectTest<MssqlRoles, MssqlUsers, MssqlUserRoles, UserRepositoryJdbcMssqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = UserRepositoryJdbcMssqlSelect(sqlClient)
}

class UserRepositoryJdbcMssqlSelect(sqlClient: JdbcSqlClient) :
    SelectRepository<MssqlRoles, MssqlUsers, MssqlUserRoles>(sqlClient, MssqlRoles, MssqlUsers, MssqlUserRoles)
