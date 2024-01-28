/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mariadb

import org.ufoss.kotysa.test.MariadbCompanies
import org.ufoss.kotysa.test.MariadbRoles
import org.ufoss.kotysa.test.MariadbUserRoles
import org.ufoss.kotysa.test.MariadbUsers
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySubQueryRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySubQueryTest

class VertxSqlClientSubQueryMariadbTest :
    AbstractVertxSqlClientMariadbTest<UserRepositoryVertxSqlClientMariadbSubQuery>(),
    MutinySubQueryTest<MariadbRoles, MariadbUsers, MariadbUserRoles, MariadbCompanies,
            UserRepositoryVertxSqlClientMariadbSubQuery> {
    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) =
        UserRepositoryVertxSqlClientMariadbSubQuery(sqlClient)
}

class UserRepositoryVertxSqlClientMariadbSubQuery(sqlClient: MutinyVertxSqlClient) :
    MutinySubQueryRepository<MariadbRoles, MariadbUsers, MariadbUserRoles, MariadbCompanies>(
        sqlClient,
        MariadbRoles,
        MariadbUsers,
        MariadbUserRoles,
        MariadbCompanies
    )
