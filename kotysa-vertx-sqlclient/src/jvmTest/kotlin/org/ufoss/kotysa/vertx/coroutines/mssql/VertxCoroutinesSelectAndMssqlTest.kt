/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mssql

import org.ufoss.kotysa.test.MssqlCompanies
import org.ufoss.kotysa.test.MssqlRoles
import org.ufoss.kotysa.test.MssqlUserRoles
import org.ufoss.kotysa.test.MssqlUsers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectAndRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectAndTest
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient


class VertxCoroutinesSelectAndMssqlTest : AbstractVertxCoroutinesMssqlTest<UserRepositoryJdbcMssqlSelectAnd>(),
    CoroutinesSelectAndTest<MssqlRoles, MssqlUsers, MssqlUserRoles, MssqlCompanies, UserRepositoryJdbcMssqlSelectAnd,
            Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) =
        UserRepositoryJdbcMssqlSelectAnd(sqlClient)
}

class UserRepositoryJdbcMssqlSelectAnd(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectAndRepository<MssqlRoles, MssqlUsers, MssqlUserRoles, MssqlCompanies>(
        sqlClient,
        MssqlRoles,
        MssqlUsers,
        MssqlUserRoles,
        MssqlCompanies
    )
