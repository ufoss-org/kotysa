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
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectBooleanRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectBooleanTest

class R2dbcSelectBooleanMariadbTest : AbstractR2dbcMariadbTest<ReactorUserRepositoryMariadbSelectBoolean>(),
    ReactorSelectBooleanTest<MariadbRoles, MariadbUsers, MariadbUserRoles, ReactorUserRepositoryMariadbSelectBoolean,
            ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MariadbReactorSqlClient, coSqlClient: MariadbCoroutinesSqlClient) =
        ReactorUserRepositoryMariadbSelectBoolean(sqlClient)
}

class ReactorUserRepositoryMariadbSelectBoolean(sqlClient: ReactorSqlClient) :
    ReactorSelectBooleanRepository<MariadbRoles, MariadbUsers, MariadbUserRoles>(
        sqlClient,
        MariadbRoles,
        MariadbUsers,
        MariadbUserRoles
    )
