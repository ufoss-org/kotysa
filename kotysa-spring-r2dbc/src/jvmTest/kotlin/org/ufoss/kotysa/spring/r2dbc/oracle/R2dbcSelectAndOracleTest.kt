/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.oracle

import org.ufoss.kotysa.OracleCoroutinesSqlClient
import org.ufoss.kotysa.OracleReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.OracleRoles
import org.ufoss.kotysa.test.OracleUserRoles
import org.ufoss.kotysa.test.OracleUsers
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectAndRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectAndTest

class JdbcSelectAndOracleTest : AbstractR2dbcOracleTest<ReactorUserRepositoryOracleSelectAnd>(),
    ReactorSelectAndTest<OracleRoles, OracleUsers, OracleUserRoles, ReactorUserRepositoryOracleSelectAnd,
            ReactorTransaction> {
    override fun instantiateRepository(sqlClient: OracleReactorSqlClient, coSqlClient: OracleCoroutinesSqlClient) =
        ReactorUserRepositoryOracleSelectAnd(sqlClient)
}

class ReactorUserRepositoryOracleSelectAnd(sqlClient: ReactorSqlClient) :
    ReactorSelectAndRepository<OracleRoles, OracleUsers, OracleUserRoles>(
        sqlClient,
        OracleRoles,
        OracleUsers,
        OracleUserRoles
    )
