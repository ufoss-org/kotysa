/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.postgresql

import org.ufoss.kotysa.test.PostgresqlCompanies
import org.ufoss.kotysa.test.PostgresqlRoles
import org.ufoss.kotysa.test.PostgresqlUserRoles
import org.ufoss.kotysa.test.PostgresqlUsers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectAndRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectAndTest
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.vertx.PostgresqlCoroutinesVertxSqlClient


class VertxCoroutinesSelectAndPostgresqlTest :
    AbstractVertxCoroutinesPostgresqlTest<UserRepositoryJdbcPostgresqlSelectAnd>(),
    CoroutinesSelectAndTest<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles, PostgresqlCompanies,
            UserRepositoryJdbcPostgresqlSelectAnd, Transaction> {
    override fun instantiateRepository(sqlClient: PostgresqlCoroutinesVertxSqlClient) =
        UserRepositoryJdbcPostgresqlSelectAnd(sqlClient)
}

class UserRepositoryJdbcPostgresqlSelectAnd(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectAndRepository<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles, PostgresqlCompanies>(
        sqlClient,
        PostgresqlRoles,
        PostgresqlUsers,
        PostgresqlUserRoles,
        PostgresqlCompanies
    )
