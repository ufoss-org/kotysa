/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.mariadb

import org.ufoss.kotysa.test.MariadbRoles
import org.ufoss.kotysa.test.MariadbUserRoles
import org.ufoss.kotysa.test.MariadbUsers
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySubQueryRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySubQueryTest

class VertxSqlClientSubQueryMariadbTest : AbstractVertxSqlClientMariadbTest<UserRepositoryVertxSqlClientMariadbSubQuery>(),
    MutinySubQueryTest<MariadbRoles, MariadbUsers, MariadbUserRoles, UserRepositoryVertxSqlClientMariadbSubQuery> {
    override fun instantiateRepository(sqlClient: VertxSqlClient) = UserRepositoryVertxSqlClientMariadbSubQuery(sqlClient)
}

class UserRepositoryVertxSqlClientMariadbSubQuery(sqlClient: VertxSqlClient) :
    MutinySubQueryRepository<MariadbRoles, MariadbUsers, MariadbUserRoles>(
        sqlClient,
        MariadbRoles,
        MariadbUsers,
        MariadbUserRoles
    )
