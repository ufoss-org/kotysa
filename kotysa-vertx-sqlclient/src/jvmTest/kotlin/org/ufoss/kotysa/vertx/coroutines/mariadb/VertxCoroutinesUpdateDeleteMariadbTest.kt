/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mariadb

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MariadbRoles
import org.ufoss.kotysa.test.MariadbUserRoles
import org.ufoss.kotysa.test.MariadbUsers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesUpdateDeleteRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesUpdateDeleteTest

class VertxCoroutinesUpdateDeleteMariadbTest : AbstractVertxCoroutinesMariadbTest<UserRepositoryJdbcMariadbUpdateDelete>(),
    CoroutinesUpdateDeleteTest<MariadbRoles, MariadbUsers, MariadbUserRoles, UserRepositoryJdbcMariadbUpdateDelete,
            Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = UserRepositoryJdbcMariadbUpdateDelete(sqlClient)
}

class UserRepositoryJdbcMariadbUpdateDelete(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesUpdateDeleteRepository<MariadbRoles, MariadbUsers, MariadbUserRoles>(
        sqlClient,
        MariadbRoles,
        MariadbUsers,
        MariadbUserRoles
    )
