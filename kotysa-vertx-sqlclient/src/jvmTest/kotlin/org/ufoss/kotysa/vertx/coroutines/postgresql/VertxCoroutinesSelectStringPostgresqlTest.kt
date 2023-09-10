/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.postgresql

import org.ufoss.kotysa.vertx.PostgresqlCoroutinesVertxSqlClient
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.PostgresqlRoles
import org.ufoss.kotysa.test.PostgresqlUserRoles
import org.ufoss.kotysa.test.PostgresqlUsers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectStringRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectStringTest


class VertxCoroutinesSelectStringPostgresqlTest : AbstractVertxCoroutinesPostgresqlTest<UserRepositoryJdbcPostgresqlSelectString>(),
    CoroutinesSelectStringTest<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles,
            UserRepositoryJdbcPostgresqlSelectString, Transaction> {
    override fun instantiateRepository(sqlClient: PostgresqlCoroutinesVertxSqlClient) =
        UserRepositoryJdbcPostgresqlSelectString(sqlClient)
}

class UserRepositoryJdbcPostgresqlSelectString(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectStringRepository<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles>(
        sqlClient,
        PostgresqlRoles,
        PostgresqlUsers,
        PostgresqlUserRoles
    )
