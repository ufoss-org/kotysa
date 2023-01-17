/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mariadb

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MariadbRoles
import org.ufoss.kotysa.test.MariadbUserRoles
import org.ufoss.kotysa.test.MariadbUsers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectOrRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectOrTest

class R2dbcSelectOrMariadbTest : AbstractR2dbcMariadbTest<UserRepositoryJdbcMariadbSelectOr>(),
    CoroutinesSelectOrTest<MariadbRoles, MariadbUsers, MariadbUserRoles, UserRepositoryJdbcMariadbSelectOr,
            R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = UserRepositoryJdbcMariadbSelectOr(sqlClient)
}

class UserRepositoryJdbcMariadbSelectOr(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectOrRepository<MariadbRoles, MariadbUsers, MariadbUserRoles>(
        sqlClient,
        MariadbRoles,
        MariadbUsers,
        MariadbUserRoles
    )
