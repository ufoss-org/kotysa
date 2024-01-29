/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mariadb

import org.ufoss.kotysa.test.MariadbCompanies
import org.ufoss.kotysa.test.MariadbRoles
import org.ufoss.kotysa.test.MariadbUserRoles
import org.ufoss.kotysa.test.MariadbUsers
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectStringRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectStringTest


class VertxSqlClientSelectStringMariadbTest :
    AbstractVertxSqlClientMariadbTest<UserRepositoryVertxSqlClientMariadbSelectString>(),
    MutinySelectStringTest<MariadbRoles, MariadbUsers, MariadbUserRoles, MariadbCompanies,
            UserRepositoryVertxSqlClientMariadbSelectString> {
    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) =
        UserRepositoryVertxSqlClientMariadbSelectString(sqlClient)
}

class UserRepositoryVertxSqlClientMariadbSelectString(sqlClient: MutinyVertxSqlClient) :
    MutinySelectStringRepository<MariadbRoles, MariadbUsers, MariadbUserRoles, MariadbCompanies>(
        sqlClient,
        MariadbRoles,
        MariadbUsers,
        MariadbUserRoles,
        MariadbCompanies
    )
