/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.mssql

import org.ufoss.kotysa.test.MssqlRoles
import org.ufoss.kotysa.test.MssqlUserRoles
import org.ufoss.kotysa.test.MssqlUsers
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySubQueryRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySubQueryTest

class VertxSqlClientSubQueryMssqlTest : AbstractVertxSqlClientMssqlTest<UserRepositoryVertxSqlClientMssqlSubQuery>(),
    MutinySubQueryTest<MssqlRoles, MssqlUsers, MssqlUserRoles, UserRepositoryVertxSqlClientMssqlSubQuery> {
    override fun instantiateRepository(sqlClient: VertxSqlClient) = UserRepositoryVertxSqlClientMssqlSubQuery(sqlClient)
}

class UserRepositoryVertxSqlClientMssqlSubQuery(sqlClient: VertxSqlClient) :
    MutinySubQueryRepository<MssqlRoles, MssqlUsers, MssqlUserRoles>(
        sqlClient,
        MssqlRoles,
        MssqlUsers,
        MssqlUserRoles
    )
