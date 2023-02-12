/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mariadb

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MariadbRoles
import org.ufoss.kotysa.test.MariadbUserRoles
import org.ufoss.kotysa.test.MariadbUsers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesUpdateDeleteRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesUpdateDeleteTest

class R2dbcUpdateDeleteMariadbTest : AbstractR2dbcMariadbTest<UserRepositoryJdbcMariadbUpdateDelete>(),
    CoroutinesUpdateDeleteTest<MariadbRoles, MariadbUsers, MariadbUserRoles, UserRepositoryJdbcMariadbUpdateDelete,
            R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = UserRepositoryJdbcMariadbUpdateDelete(sqlClient)
}

class UserRepositoryJdbcMariadbUpdateDelete(sqlClient: R2dbcSqlClient) :
    CoroutinesUpdateDeleteRepository<MariadbRoles, MariadbUsers, MariadbUserRoles>(
        sqlClient,
        MariadbRoles,
        MariadbUsers,
        MariadbUserRoles
    )
