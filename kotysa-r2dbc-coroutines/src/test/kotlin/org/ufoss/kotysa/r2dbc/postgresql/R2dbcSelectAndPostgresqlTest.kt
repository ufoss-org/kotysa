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
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectAndRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectAndTest


class R2dbcSelectAndPostgresqlTest : AbstractR2dbcPostgresqlTest<UserRepositoryJdbcPostgresqlSelectAnd>(),
    CoroutinesSelectAndTest<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles,
            UserRepositoryJdbcPostgresqlSelectAnd, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: PostgresqlR2dbcSqlClient) =
        UserRepositoryJdbcPostgresqlSelectAnd(sqlClient)
}

class UserRepositoryJdbcPostgresqlSelectAnd(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectAndRepository<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles>(
        sqlClient,
        PostgresqlRoles,
        PostgresqlUsers,
        PostgresqlUserRoles
    )
