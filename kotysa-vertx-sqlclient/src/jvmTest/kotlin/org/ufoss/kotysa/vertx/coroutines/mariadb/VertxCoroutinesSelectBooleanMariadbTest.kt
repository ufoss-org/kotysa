/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mariadb

import org.ufoss.kotysa.test.MariadbCompanies
import org.ufoss.kotysa.test.MariadbRoles
import org.ufoss.kotysa.test.MariadbUserRoles
import org.ufoss.kotysa.test.MariadbUsers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectBooleanRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectBooleanTest
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient


class VertxCoroutinesSelectBooleanMariadbTest :
    AbstractVertxCoroutinesMariadbTest<UserRepositoryJdbcMariadbSelectBoolean>(),
    CoroutinesSelectBooleanTest<MariadbRoles, MariadbUsers, MariadbUserRoles, MariadbCompanies,
            UserRepositoryJdbcMariadbSelectBoolean, Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) =
        UserRepositoryJdbcMariadbSelectBoolean(sqlClient)
}

class UserRepositoryJdbcMariadbSelectBoolean(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectBooleanRepository<MariadbRoles, MariadbUsers, MariadbUserRoles, MariadbCompanies>(
        sqlClient,
        MariadbRoles,
        MariadbUsers,
        MariadbUserRoles,
        MariadbCompanies
    )
