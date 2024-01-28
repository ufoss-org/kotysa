/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mssql

import org.ufoss.kotysa.test.MssqlCompanies
import org.ufoss.kotysa.test.MssqlRoles
import org.ufoss.kotysa.test.MssqlUserRoles
import org.ufoss.kotysa.test.MssqlUsers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectOrRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectOrTest
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient

class VertxCoroutinesSelectOrMssqlTest : AbstractVertxCoroutinesMssqlTest<UserRepositoryJdbcMssqlSelectOr>(),
    CoroutinesSelectOrTest<MssqlRoles, MssqlUsers, MssqlUserRoles, MssqlCompanies, UserRepositoryJdbcMssqlSelectOr,
            Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = UserRepositoryJdbcMssqlSelectOr(sqlClient)
}

class UserRepositoryJdbcMssqlSelectOr(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectOrRepository<MssqlRoles, MssqlUsers, MssqlUserRoles, MssqlCompanies>(
        sqlClient,
        MssqlRoles,
        MssqlUsers,
        MssqlUserRoles,
        MssqlCompanies
    )
