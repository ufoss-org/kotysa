/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mysql

import org.ufoss.kotysa.test.MysqlRoles
import org.ufoss.kotysa.test.MysqlUserRoles
import org.ufoss.kotysa.test.MysqlUsers
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySubQueryRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySubQueryTest

class VertxSqlClientSubQueryMysqlTest : AbstractVertxSqlClientMysqlTest<UserRepositoryVertxSqlClientMysqlSubQuery>(),
    MutinySubQueryTest<MysqlRoles, MysqlUsers, MysqlUserRoles, UserRepositoryVertxSqlClientMysqlSubQuery> {
    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = UserRepositoryVertxSqlClientMysqlSubQuery(sqlClient)
}

class UserRepositoryVertxSqlClientMysqlSubQuery(sqlClient: MutinyVertxSqlClient) :
    MutinySubQueryRepository<MysqlRoles, MysqlUsers, MysqlUserRoles>(
        sqlClient,
        MysqlRoles,
        MysqlUsers,
        MysqlUserRoles
    )
