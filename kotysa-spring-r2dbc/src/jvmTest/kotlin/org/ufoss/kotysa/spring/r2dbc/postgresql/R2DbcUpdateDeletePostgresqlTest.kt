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
import org.ufoss.kotysa.test.repositories.reactor.ReactorUpdateDeleteRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorUpdateDeleteTest

class R2dbcUpdateDeletePostgresqlTest : AbstractR2dbcPostgresqlTest<UserRepositoryR2dbcPostgresqlUpdateDelete>(),
    ReactorUpdateDeleteTest<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles, UserRepositoryR2dbcPostgresqlUpdateDelete,
            ReactorTransaction> {
    override fun instantiateRepository(sqlClient: PostgresqlReactorSqlClient, coSqlClient: PostgresqlCoroutinesSqlClient) =
        UserRepositoryR2dbcPostgresqlUpdateDelete(sqlClient)
}

class UserRepositoryR2dbcPostgresqlUpdateDelete(sqlClient: ReactorSqlClient) :
    ReactorUpdateDeleteRepository<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles>(
        sqlClient,
        PostgresqlRoles,
        PostgresqlUsers,
        PostgresqlUserRoles
    )
