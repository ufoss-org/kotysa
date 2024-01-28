/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mariadb

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MariadbCompanies
import org.ufoss.kotysa.test.MariadbRoles
import org.ufoss.kotysa.test.MariadbUserRoles
import org.ufoss.kotysa.test.MariadbUsers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSubQueryRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSubQueryTest

class R2dbcSubQueryMariadbTest : AbstractR2dbcMariadbTest<UserRepositoryR2dbcMariadbSubQuery>(),
    CoroutinesSubQueryTest<MariadbRoles, MariadbUsers, MariadbUserRoles, MariadbCompanies,
            UserRepositoryR2dbcMariadbSubQuery, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = UserRepositoryR2dbcMariadbSubQuery(sqlClient)
}

class UserRepositoryR2dbcMariadbSubQuery(sqlClient: R2dbcSqlClient) :
    CoroutinesSubQueryRepository<MariadbRoles, MariadbUsers, MariadbUserRoles, MariadbCompanies>(
        sqlClient,
        MariadbRoles,
        MariadbUsers,
        MariadbUserRoles,
        MariadbCompanies
    )
