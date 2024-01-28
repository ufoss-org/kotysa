/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.postgresql

import org.ufoss.kotysa.test.PostgresqlCompanies
import org.ufoss.kotysa.test.PostgresqlRoles
import org.ufoss.kotysa.test.PostgresqlUserRoles
import org.ufoss.kotysa.test.PostgresqlUsers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectBooleanRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectBooleanTest
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.vertx.PostgresqlCoroutinesVertxSqlClient


class VertxCoroutinesSelectBooleanPostgresqlTest :
    AbstractVertxCoroutinesPostgresqlTest<UserRepositoryJdbcPostgresqlSelectBoolean>(),
    CoroutinesSelectBooleanTest<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles, PostgresqlCompanies,
            UserRepositoryJdbcPostgresqlSelectBoolean, Transaction> {
    override fun instantiateRepository(sqlClient: PostgresqlCoroutinesVertxSqlClient) =
        UserRepositoryJdbcPostgresqlSelectBoolean(sqlClient)
}

class UserRepositoryJdbcPostgresqlSelectBoolean(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectBooleanRepository<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles, PostgresqlCompanies>(
        sqlClient,
        PostgresqlRoles,
        PostgresqlUsers,
        PostgresqlUserRoles,
        PostgresqlCompanies
    )
