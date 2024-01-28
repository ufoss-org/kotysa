/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mariadb

import org.ufoss.kotysa.MariadbCoroutinesSqlClient
import org.ufoss.kotysa.MariadbReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MariadbCompanies
import org.ufoss.kotysa.test.MariadbRoles
import org.ufoss.kotysa.test.MariadbUserRoles
import org.ufoss.kotysa.test.MariadbUsers
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectAndRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectAndTest

class R2DbcSelectAndMariadbTest : AbstractR2dbcMariadbTest<ReactorUserRepositoryMariadbSelectAnd>(),
    ReactorSelectAndTest<MariadbRoles, MariadbUsers, MariadbUserRoles, MariadbCompanies,
            ReactorUserRepositoryMariadbSelectAnd, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MariadbReactorSqlClient, coSqlClient: MariadbCoroutinesSqlClient) =
        ReactorUserRepositoryMariadbSelectAnd(sqlClient)
}

class ReactorUserRepositoryMariadbSelectAnd(sqlClient: ReactorSqlClient) :
    ReactorSelectAndRepository<MariadbRoles, MariadbUsers, MariadbUserRoles, MariadbCompanies>(
        sqlClient,
        MariadbRoles,
        MariadbUsers,
        MariadbUserRoles,
        MariadbCompanies
    )
