/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.postgresql

import org.ufoss.kotysa.PostgresqlCoroutinesSqlClient
import org.ufoss.kotysa.PostgresqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.PostgresqlCompanies
import org.ufoss.kotysa.test.PostgresqlRoles
import org.ufoss.kotysa.test.PostgresqlUserRoles
import org.ufoss.kotysa.test.PostgresqlUsers
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectBooleanRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectBooleanTest

class R2dbcSelectBooleanPostgresqlTest : AbstractR2dbcPostgresqlTest<ReactorUserRepositoryPostgresqlSelectBoolean>(),
    ReactorSelectBooleanTest<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles, PostgresqlCompanies,
            ReactorUserRepositoryPostgresqlSelectBoolean, ReactorTransaction> {
    override fun instantiateRepository(
        sqlClient: PostgresqlReactorSqlClient,
        coSqlClient: PostgresqlCoroutinesSqlClient
    ) =
        ReactorUserRepositoryPostgresqlSelectBoolean(sqlClient)
}

class ReactorUserRepositoryPostgresqlSelectBoolean(sqlClient: ReactorSqlClient) :
    ReactorSelectBooleanRepository<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles, PostgresqlCompanies>(
        sqlClient,
        PostgresqlRoles,
        PostgresqlUsers,
        PostgresqlUserRoles,
        PostgresqlCompanies
    )
