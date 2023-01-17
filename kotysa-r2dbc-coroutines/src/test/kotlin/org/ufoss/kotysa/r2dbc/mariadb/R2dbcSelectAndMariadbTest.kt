/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mariadb

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MariadbRoles
import org.ufoss.kotysa.test.MariadbUserRoles
import org.ufoss.kotysa.test.MariadbUsers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectAndRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectAndTest


class R2dbcSelectAndMariadbTest : AbstractR2dbcMariadbTest<UserRepositoryJdbcMariadbSelectAnd>(),
    CoroutinesSelectAndTest<MariadbRoles, MariadbUsers, MariadbUserRoles, UserRepositoryJdbcMariadbSelectAnd,
            R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = UserRepositoryJdbcMariadbSelectAnd(sqlClient)
}

class UserRepositoryJdbcMariadbSelectAnd(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectAndRepository<MariadbRoles, MariadbUsers, MariadbUserRoles>(
        sqlClient,
        MariadbRoles,
        MariadbUsers,
        MariadbUserRoles
    )
