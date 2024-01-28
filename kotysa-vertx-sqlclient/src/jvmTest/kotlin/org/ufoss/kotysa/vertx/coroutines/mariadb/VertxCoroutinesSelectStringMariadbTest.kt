/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mariadb

import org.ufoss.kotysa.test.MariadbCompanies
import org.ufoss.kotysa.test.MariadbRoles
import org.ufoss.kotysa.test.MariadbUserRoles
import org.ufoss.kotysa.test.MariadbUsers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectStringRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectStringTest
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient


class VertxCoroutinesSelectStringMariadbTest :
    AbstractVertxCoroutinesMariadbTest<UserRepositoryJdbcMariadbSelectString>(),
    CoroutinesSelectStringTest<MariadbRoles, MariadbUsers, MariadbUserRoles, MariadbCompanies,
            UserRepositoryJdbcMariadbSelectString, Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) =
        UserRepositoryJdbcMariadbSelectString(sqlClient)
}

class UserRepositoryJdbcMariadbSelectString(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectStringRepository<MariadbRoles, MariadbUsers, MariadbUserRoles, MariadbCompanies>(
        sqlClient,
        MariadbRoles,
        MariadbUsers,
        MariadbUserRoles,
        MariadbCompanies
    )
