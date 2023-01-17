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
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesUpdateDeleteRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesUpdateDeleteTest

class R2dbcUpdateDeletePostgresqlTest : AbstractR2dbcPostgresqlTest<UserRepositoryJdbcPostgresqlUpdateDelete>(),
    CoroutinesUpdateDeleteTest<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles,
            UserRepositoryJdbcPostgresqlUpdateDelete, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: PostgresqlR2dbcSqlClient) =
        UserRepositoryJdbcPostgresqlUpdateDelete(sqlClient)
}

class UserRepositoryJdbcPostgresqlUpdateDelete(sqlClient: R2dbcSqlClient) :
    CoroutinesUpdateDeleteRepository<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles>(
        sqlClient,
        PostgresqlRoles,
        PostgresqlUsers,
        PostgresqlUserRoles
    )
