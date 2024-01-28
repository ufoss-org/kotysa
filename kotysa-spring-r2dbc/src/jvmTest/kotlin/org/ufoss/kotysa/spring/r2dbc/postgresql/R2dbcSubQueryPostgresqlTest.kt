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
import org.ufoss.kotysa.test.repositories.reactor.ReactorSubQueryRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSubQueryTest

class R2dbcSubQueryPostgresqlTest : AbstractR2dbcPostgresqlTest<UserRepositoryR2dbcPostgresqlSubQuery>(),
    ReactorSubQueryTest<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles, PostgresqlCompanies,
            UserRepositoryR2dbcPostgresqlSubQuery, ReactorTransaction> {

    override fun instantiateRepository(
        sqlClient: PostgresqlReactorSqlClient,
        coSqlClient: PostgresqlCoroutinesSqlClient,
    ) = UserRepositoryR2dbcPostgresqlSubQuery(sqlClient)
}


class UserRepositoryR2dbcPostgresqlSubQuery(sqlClient: ReactorSqlClient) :
    ReactorSubQueryRepository<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles, PostgresqlCompanies>(
        sqlClient,
        PostgresqlRoles,
        PostgresqlUsers,
        PostgresqlUserRoles,
        PostgresqlCompanies
    )
