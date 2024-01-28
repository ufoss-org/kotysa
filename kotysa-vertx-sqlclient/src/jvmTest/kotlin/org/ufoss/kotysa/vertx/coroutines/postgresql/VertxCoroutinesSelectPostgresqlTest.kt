/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.postgresql

import org.ufoss.kotysa.test.PostgresqlCompanies
import org.ufoss.kotysa.test.PostgresqlRoles
import org.ufoss.kotysa.test.PostgresqlUserRoles
import org.ufoss.kotysa.test.PostgresqlUsers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectTest
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.vertx.PostgresqlCoroutinesVertxSqlClient


class VertxCoroutinesSelectPostgresqlTest : AbstractVertxCoroutinesPostgresqlTest<UserRepositoryJdbcPostgresqlSelect>(),
    CoroutinesSelectTest<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles, PostgresqlCompanies,
            UserRepositoryJdbcPostgresqlSelect, Transaction> {
    override fun instantiateRepository(sqlClient: PostgresqlCoroutinesVertxSqlClient) =
        UserRepositoryJdbcPostgresqlSelect(sqlClient)
}

class UserRepositoryJdbcPostgresqlSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectRepository<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles, PostgresqlCompanies>(
        sqlClient,
        PostgresqlRoles,
        PostgresqlUsers,
        PostgresqlUserRoles,
        PostgresqlCompanies
    )
