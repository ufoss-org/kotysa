/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mssql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MssqlRoles
import org.ufoss.kotysa.test.MssqlUserRoles
import org.ufoss.kotysa.test.MssqlUsers
import org.ufoss.kotysa.test.repositories.blocking.SelectDistinctRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectDistinctTest

class JdbcSelectDistinctMssqlTest : AbstractJdbcMssqlTest<UserRepositoryJdbcMssqlSelectDistinct>(),
    SelectDistinctTest<MssqlRoles, MssqlUsers, MssqlUserRoles, UserRepositoryJdbcMssqlSelectDistinct, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = UserRepositoryJdbcMssqlSelectDistinct(sqlClient)
}

class UserRepositoryJdbcMssqlSelectDistinct(sqlClient: JdbcSqlClient) :
    SelectDistinctRepository<MssqlRoles, MssqlUsers, MssqlUserRoles>(sqlClient, MssqlRoles, MssqlUsers, MssqlUserRoles)
