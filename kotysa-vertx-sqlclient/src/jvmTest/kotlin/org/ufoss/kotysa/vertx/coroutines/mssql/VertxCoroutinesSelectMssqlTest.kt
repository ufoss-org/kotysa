/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mssql

import org.ufoss.kotysa.test.MssqlCompanies
import org.ufoss.kotysa.test.MssqlRoles
import org.ufoss.kotysa.test.MssqlUserRoles
import org.ufoss.kotysa.test.MssqlUsers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectTest
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient


class VertxCoroutinesSelectMssqlTest : AbstractVertxCoroutinesMssqlTest<UserRepositoryJdbcMssqlSelect>(),
    CoroutinesSelectTest<MssqlRoles, MssqlUsers, MssqlUserRoles, MssqlCompanies, UserRepositoryJdbcMssqlSelect,
            Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = UserRepositoryJdbcMssqlSelect(sqlClient)
}

class UserRepositoryJdbcMssqlSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectRepository<MssqlRoles, MssqlUsers, MssqlUserRoles, MssqlCompanies>(
        sqlClient,
        MssqlRoles,
        MssqlUsers,
        MssqlUserRoles,
        MssqlCompanies
    )
