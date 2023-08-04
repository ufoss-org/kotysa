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
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectOrRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectOrTest

class R2dbcSelectOrPostgresqlTest : AbstractR2dbcPostgresqlTest<UserRepositoryR2dbcPostgresqlSelectOr>(),
    ReactorSelectOrTest<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles, UserRepositoryR2dbcPostgresqlSelectOr,
            ReactorTransaction> {
    override fun instantiateRepository(sqlClient: PostgresqlReactorSqlClient, coSqlClient: PostgresqlCoroutinesSqlClient) =
        UserRepositoryR2dbcPostgresqlSelectOr(sqlClient)
}

class UserRepositoryR2dbcPostgresqlSelectOr(sqlClient: ReactorSqlClient) :
    ReactorSelectOrRepository<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles>(
        sqlClient,
        PostgresqlRoles,
        PostgresqlUsers,
        PostgresqlUserRoles
    )
