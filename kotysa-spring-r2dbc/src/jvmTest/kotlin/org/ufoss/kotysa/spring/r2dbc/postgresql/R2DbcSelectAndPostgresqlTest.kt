/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.postgresql

import org.ufoss.kotysa.PostgresqlCoroutinesSqlClient
import org.ufoss.kotysa.PostgresqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.PostgresqlRoles
import org.ufoss.kotysa.test.PostgresqlUserRoles
import org.ufoss.kotysa.test.PostgresqlUsers
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectAndRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectAndTest

class R2DbcSelectAndPostgresqlTest : AbstractR2dbcPostgresqlTest<ReactorUserRepositoryPostgresqlSelectAnd>(),
    ReactorSelectAndTest<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles, ReactorUserRepositoryPostgresqlSelectAnd,
            ReactorTransaction> {
    override fun instantiateRepository(sqlClient: PostgresqlReactorSqlClient, coSqlClient: PostgresqlCoroutinesSqlClient) =
        ReactorUserRepositoryPostgresqlSelectAnd(sqlClient)
}

class ReactorUserRepositoryPostgresqlSelectAnd(sqlClient: ReactorSqlClient) :
    ReactorSelectAndRepository<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles>(
        sqlClient,
        PostgresqlRoles,
        PostgresqlUsers,
        PostgresqlUserRoles
    )
