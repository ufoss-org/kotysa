/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.mysql

import org.ufoss.kotysa.test.MysqlRoles
import org.ufoss.kotysa.test.MysqlUserRoles
import org.ufoss.kotysa.test.MysqlUsers
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySubQueryRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySubQueryTest

class VertxSqlClientSubQueryMysqlTest : AbstractVertxSqlClientMysqlTest<UserRepositoryVertxSqlClientMysqlSubQuery>(),
    MutinySubQueryTest<MysqlRoles, MysqlUsers, MysqlUserRoles, UserRepositoryVertxSqlClientMysqlSubQuery> {
    override fun instantiateRepository(sqlClient: VertxSqlClient) = UserRepositoryVertxSqlClientMysqlSubQuery(sqlClient)
}

class UserRepositoryVertxSqlClientMysqlSubQuery(sqlClient: VertxSqlClient) :
    MutinySubQueryRepository<MysqlRoles, MysqlUsers, MysqlUserRoles>(
        sqlClient,
        MysqlRoles,
        MysqlUsers,
        MysqlUserRoles
    )
