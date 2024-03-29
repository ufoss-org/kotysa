/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mssql

import org.ufoss.kotysa.test.MssqlCompanies
import org.ufoss.kotysa.test.MssqlRoles
import org.ufoss.kotysa.test.MssqlUserRoles
import org.ufoss.kotysa.test.MssqlUsers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectDistinctRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectDistinctTest
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient

class VertxCoroutinesSelectDistinctMssqlTest :
    AbstractVertxCoroutinesMssqlTest<UserRepositoryJdbcMssqlSelectDistinct>(),
    CoroutinesSelectDistinctTest<MssqlRoles, MssqlUsers, MssqlUserRoles, MssqlCompanies,
            UserRepositoryJdbcMssqlSelectDistinct, Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) =
        UserRepositoryJdbcMssqlSelectDistinct(sqlClient)
}

class UserRepositoryJdbcMssqlSelectDistinct(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectDistinctRepository<MssqlRoles, MssqlUsers, MssqlUserRoles, MssqlCompanies>(
        sqlClient,
        MssqlRoles,
        MssqlUsers,
        MssqlUserRoles,
        MssqlCompanies
    )
