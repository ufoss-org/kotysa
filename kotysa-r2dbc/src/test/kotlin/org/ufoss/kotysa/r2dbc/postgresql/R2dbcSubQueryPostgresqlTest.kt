/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import org.ufoss.kotysa.PostgresqlR2dbcSqlClient
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.PostgresqlRoles
import org.ufoss.kotysa.test.PostgresqlUserRoles
import org.ufoss.kotysa.test.PostgresqlUsers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSubQueryRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSubQueryTest

class R2dbcSubQueryPostgresqlTest : AbstractR2dbcPostgresqlTest<UserRepositoryR2dbcPostgresqlSubQuery>(),
    CoroutinesSubQueryTest<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles, UserRepositoryR2dbcPostgresqlSubQuery,
            R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: PostgresqlR2dbcSqlClient) =
        UserRepositoryR2dbcPostgresqlSubQuery(sqlClient)
}

class UserRepositoryR2dbcPostgresqlSubQuery(sqlClient: R2dbcSqlClient) :
    CoroutinesSubQueryRepository<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles>(
        sqlClient,
        PostgresqlRoles,
        PostgresqlUsers,
        PostgresqlUserRoles
    )
