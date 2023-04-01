/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mssql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MssqlRoles
import org.ufoss.kotysa.test.MssqlUserRoles
import org.ufoss.kotysa.test.MssqlUsers
import org.ufoss.kotysa.test.repositories.blocking.UpdateDeleteRepository
import org.ufoss.kotysa.test.repositories.blocking.UpdateDeleteTest

class JdbcUpdateDeleteMssqlTest : AbstractJdbcMssqlTest<UserRepositoryJdbcMssqlUpdateDelete>(),
    UpdateDeleteTest<MssqlRoles, MssqlUsers, MssqlUserRoles, UserRepositoryJdbcMssqlUpdateDelete, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = UserRepositoryJdbcMssqlUpdateDelete(sqlClient)
}

class UserRepositoryJdbcMssqlUpdateDelete(sqlClient: JdbcSqlClient) :
    UpdateDeleteRepository<MssqlRoles, MssqlUsers, MssqlUserRoles>(sqlClient, MssqlRoles, MssqlUsers, MssqlUserRoles)
