/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mssql

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MssqlRoles
import org.ufoss.kotysa.test.MssqlUserRoles
import org.ufoss.kotysa.test.MssqlUsers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSubQueryRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSubQueryTest

class VertxCoroutinesSubQueryMssqlTest : AbstractVertxCoroutinesMssqlTest<UserRepositoryVertxCoroutinesMssqlSubQuery>(),
    CoroutinesSubQueryTest<MssqlRoles, MssqlUsers, MssqlUserRoles, UserRepositoryVertxCoroutinesMssqlSubQuery,
            Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = UserRepositoryVertxCoroutinesMssqlSubQuery(sqlClient)
}

class UserRepositoryVertxCoroutinesMssqlSubQuery(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSubQueryRepository<MssqlRoles, MssqlUsers, MssqlUserRoles>(
        sqlClient,
        MssqlRoles,
        MssqlUsers,
        MssqlUserRoles
    )
