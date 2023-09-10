/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mssql

import org.ufoss.kotysa.test.MssqlRoles
import org.ufoss.kotysa.test.MssqlUserRoles
import org.ufoss.kotysa.test.MssqlUsers
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySubQueryRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySubQueryTest

class VertxSqlClientSubQueryMssqlTest : AbstractVertxSqlClientMssqlTest<UserRepositoryVertxSqlClientMssqlSubQuery>(),
    MutinySubQueryTest<MssqlRoles, MssqlUsers, MssqlUserRoles, UserRepositoryVertxSqlClientMssqlSubQuery> {
    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = UserRepositoryVertxSqlClientMssqlSubQuery(sqlClient)
}

class UserRepositoryVertxSqlClientMssqlSubQuery(sqlClient: MutinyVertxSqlClient) :
    MutinySubQueryRepository<MssqlRoles, MssqlUsers, MssqlUserRoles>(
        sqlClient,
        MssqlRoles,
        MssqlUsers,
        MssqlUserRoles
    )
