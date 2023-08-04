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
import org.ufoss.kotysa.test.repositories.reactor.ReactorUpdateDeleteRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorUpdateDeleteTest

class R2dbcUpdateDeleteMariadbTest : AbstractR2dbcMariadbTest<UserRepositoryR2dbcMariadbUpdateDelete>(),
    ReactorUpdateDeleteTest<MariadbRoles, MariadbUsers, MariadbUserRoles, UserRepositoryR2dbcMariadbUpdateDelete,
            ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MariadbReactorSqlClient, coSqlClient: MariadbCoroutinesSqlClient) =
        UserRepositoryR2dbcMariadbUpdateDelete(sqlClient)
}

class UserRepositoryR2dbcMariadbUpdateDelete(sqlClient: ReactorSqlClient) :
    ReactorUpdateDeleteRepository<MariadbRoles, MariadbUsers, MariadbUserRoles>(
        sqlClient,
        MariadbRoles,
        MariadbUsers,
        MariadbUserRoles
    )
