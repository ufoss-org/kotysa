/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.postgresql

import org.ufoss.kotysa.test.PostgresqlCompanies
import org.ufoss.kotysa.test.PostgresqlRoles
import org.ufoss.kotysa.test.PostgresqlUserRoles
import org.ufoss.kotysa.test.PostgresqlUsers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesUpdateDeleteRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesUpdateDeleteTest
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.vertx.PostgresqlCoroutinesVertxSqlClient

class VertxCoroutinesUpdateDeletePostgresqlTest :
    AbstractVertxCoroutinesPostgresqlTest<UserRepositoryJdbcPostgresqlUpdateDelete>(),
    CoroutinesUpdateDeleteTest<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles, PostgresqlCompanies,
            UserRepositoryJdbcPostgresqlUpdateDelete, Transaction> {
    override fun instantiateRepository(sqlClient: PostgresqlCoroutinesVertxSqlClient) =
        UserRepositoryJdbcPostgresqlUpdateDelete(sqlClient)
}

class UserRepositoryJdbcPostgresqlUpdateDelete(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesUpdateDeleteRepository<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles, PostgresqlCompanies>(
        sqlClient,
        PostgresqlRoles,
        PostgresqlUsers,
        PostgresqlUserRoles,
        PostgresqlCompanies
    )
