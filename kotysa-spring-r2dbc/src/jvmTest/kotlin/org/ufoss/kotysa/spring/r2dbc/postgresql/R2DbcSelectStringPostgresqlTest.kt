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
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectStringRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectStringTest

class R2dbcSelectStringPostgresqlTest : AbstractR2dbcPostgresqlTest<UserRepositoryR2dbcPostgresqlSelectString>(),
    ReactorSelectStringTest<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles, UserRepositoryR2dbcPostgresqlSelectString,
            ReactorTransaction> {
    override fun instantiateRepository(sqlClient: PostgresqlReactorSqlClient, coSqlClient: PostgresqlCoroutinesSqlClient) =
        UserRepositoryR2dbcPostgresqlSelectString(sqlClient)
}

class UserRepositoryR2dbcPostgresqlSelectString(sqlClient: ReactorSqlClient) :
    ReactorSelectStringRepository<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles>(
        sqlClient,
        PostgresqlRoles,
        PostgresqlUsers,
        PostgresqlUserRoles
    )
