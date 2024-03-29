/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mssql

import org.ufoss.kotysa.test.MssqlCompanies
import org.ufoss.kotysa.test.MssqlRoles
import org.ufoss.kotysa.test.MssqlUserRoles
import org.ufoss.kotysa.test.MssqlUsers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectBooleanRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectBooleanTest
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient


class VertxCoroutinesSelectBooleanMssqlTest : AbstractVertxCoroutinesMssqlTest<UserRepositoryJdbcMssqlSelectBoolean>(),
    CoroutinesSelectBooleanTest<MssqlRoles, MssqlUsers, MssqlUserRoles, MssqlCompanies,
            UserRepositoryJdbcMssqlSelectBoolean, Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) =
        UserRepositoryJdbcMssqlSelectBoolean(sqlClient)
}

class UserRepositoryJdbcMssqlSelectBoolean(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectBooleanRepository<MssqlRoles, MssqlUsers, MssqlUserRoles, MssqlCompanies>(
        sqlClient,
        MssqlRoles,
        MssqlUsers,
        MssqlUserRoles,
        MssqlCompanies
    )
