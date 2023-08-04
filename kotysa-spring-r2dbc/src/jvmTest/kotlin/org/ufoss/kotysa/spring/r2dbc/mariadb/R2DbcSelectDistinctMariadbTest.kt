/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mariadb

import org.ufoss.kotysa.MariadbCoroutinesSqlClient
import org.ufoss.kotysa.MariadbReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MariadbRoles
import org.ufoss.kotysa.test.MariadbUserRoles
import org.ufoss.kotysa.test.MariadbUsers
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectDistinctRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectDistinctTest

class R2dbcSelectDistinctMariadbTest : AbstractR2dbcMariadbTest<UserRepositoryR2dbcMariadbSelectDistinct>(),
    ReactorSelectDistinctTest<MariadbRoles, MariadbUsers, MariadbUserRoles, UserRepositoryR2dbcMariadbSelectDistinct,
            ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MariadbReactorSqlClient, coSqlClient: MariadbCoroutinesSqlClient) =
        UserRepositoryR2dbcMariadbSelectDistinct(sqlClient)
}

class UserRepositoryR2dbcMariadbSelectDistinct(sqlClient: ReactorSqlClient) :
    ReactorSelectDistinctRepository<MariadbRoles, MariadbUsers, MariadbUserRoles>(
        sqlClient,
        MariadbRoles,
        MariadbUsers,
        MariadbUserRoles
    )
