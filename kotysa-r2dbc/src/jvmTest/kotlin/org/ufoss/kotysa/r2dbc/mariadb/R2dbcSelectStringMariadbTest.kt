/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mariadb

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MariadbRoles
import org.ufoss.kotysa.test.MariadbUserRoles
import org.ufoss.kotysa.test.MariadbUsers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectStringRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectStringTest


class R2dbcSelectStringMariadbTest : AbstractR2dbcMariadbTest<UserRepositoryJdbcMariadbSelectString>(),
    CoroutinesSelectStringTest<MariadbRoles, MariadbUsers, MariadbUserRoles, UserRepositoryJdbcMariadbSelectString,
            R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = UserRepositoryJdbcMariadbSelectString(sqlClient)
}

class UserRepositoryJdbcMariadbSelectString(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectStringRepository<MariadbRoles, MariadbUsers, MariadbUserRoles>(
        sqlClient,
        MariadbRoles,
        MariadbUsers,
        MariadbUserRoles
    )
