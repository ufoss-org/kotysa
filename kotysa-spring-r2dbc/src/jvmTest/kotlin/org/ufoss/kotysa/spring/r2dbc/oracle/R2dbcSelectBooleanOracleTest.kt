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
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectBooleanRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectBooleanTest

class JdbcSelectBooleanOracleTest : AbstractR2dbcOracleTest<ReactorUserRepositoryOracleSelectBoolean>(),
    ReactorSelectBooleanTest<OracleRoles, OracleUsers, OracleUserRoles, ReactorUserRepositoryOracleSelectBoolean,
            ReactorTransaction> {
    override fun instantiateRepository(sqlClient: OracleReactorSqlClient, coSqlClient: OracleCoroutinesSqlClient) =
        ReactorUserRepositoryOracleSelectBoolean(sqlClient)
}

class ReactorUserRepositoryOracleSelectBoolean(sqlClient: ReactorSqlClient) :
    ReactorSelectBooleanRepository<OracleRoles, OracleUsers, OracleUserRoles>(
        sqlClient,
        OracleRoles,
        OracleUsers,
        OracleUserRoles
    )
