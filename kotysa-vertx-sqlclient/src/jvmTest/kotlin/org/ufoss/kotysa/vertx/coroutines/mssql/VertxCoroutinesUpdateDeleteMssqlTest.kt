/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mssql

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MssqlRoles
import org.ufoss.kotysa.test.MssqlUserRoles
import org.ufoss.kotysa.test.MssqlUsers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesUpdateDeleteRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesUpdateDeleteTest

class VertxCoroutinesUpdateDeleteMssqlTest : AbstractVertxCoroutinesMssqlTest<UserRepositoryJdbcMssqlUpdateDelete>(),
    CoroutinesUpdateDeleteTest<MssqlRoles, MssqlUsers, MssqlUserRoles, UserRepositoryJdbcMssqlUpdateDelete,
            Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = UserRepositoryJdbcMssqlUpdateDelete(sqlClient)
}

class UserRepositoryJdbcMssqlUpdateDelete(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesUpdateDeleteRepository<MssqlRoles, MssqlUsers, MssqlUserRoles>(
        sqlClient,
        MssqlRoles,
        MssqlUsers,
        MssqlUserRoles
    )
