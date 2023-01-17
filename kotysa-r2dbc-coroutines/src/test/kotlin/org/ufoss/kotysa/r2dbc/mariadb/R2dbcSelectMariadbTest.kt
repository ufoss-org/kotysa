/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mariadb

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MariadbRoles
import org.ufoss.kotysa.test.MariadbUserRoles
import org.ufoss.kotysa.test.MariadbUsers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectTest


class R2dbcSelectMariadbTest : AbstractR2dbcMariadbTest<UserRepositoryJdbcMariadbSelect>(),
    CoroutinesSelectTest<MariadbRoles, MariadbUsers, MariadbUserRoles, UserRepositoryJdbcMariadbSelect, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = UserRepositoryJdbcMariadbSelect(sqlClient)
}

class UserRepositoryJdbcMariadbSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectRepository<MariadbRoles, MariadbUsers, MariadbUserRoles>(
        sqlClient,
        MariadbRoles,
        MariadbUsers,
        MariadbUserRoles
    )
