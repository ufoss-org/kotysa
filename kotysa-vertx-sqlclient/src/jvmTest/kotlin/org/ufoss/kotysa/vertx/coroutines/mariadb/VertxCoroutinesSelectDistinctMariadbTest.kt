/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mariadb

import org.ufoss.kotysa.test.MariadbCompanies
import org.ufoss.kotysa.test.MariadbRoles
import org.ufoss.kotysa.test.MariadbUserRoles
import org.ufoss.kotysa.test.MariadbUsers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectDistinctRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectDistinctTest
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient

class VertxCoroutinesSelectDistinctMariadbTest :
    AbstractVertxCoroutinesMariadbTest<UserRepositoryJdbcMariadbSelectDistinct>(),
    CoroutinesSelectDistinctTest<MariadbRoles, MariadbUsers, MariadbUserRoles, MariadbCompanies,
            UserRepositoryJdbcMariadbSelectDistinct, Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) =
        UserRepositoryJdbcMariadbSelectDistinct(sqlClient)
}

class UserRepositoryJdbcMariadbSelectDistinct(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectDistinctRepository<MariadbRoles, MariadbUsers, MariadbUserRoles, MariadbCompanies>(
        sqlClient,
        MariadbRoles,
        MariadbUsers,
        MariadbUserRoles,
        MariadbCompanies
    )
