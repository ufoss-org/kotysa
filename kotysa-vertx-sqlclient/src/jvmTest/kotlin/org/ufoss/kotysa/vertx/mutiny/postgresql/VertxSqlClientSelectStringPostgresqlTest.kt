/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.postgresql

import org.ufoss.kotysa.test.PostgresqlCompanies
import org.ufoss.kotysa.test.PostgresqlRoles
import org.ufoss.kotysa.test.PostgresqlUserRoles
import org.ufoss.kotysa.test.PostgresqlUsers
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.PostgresqlMutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectStringRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectStringTest


class VertxSqlClientSelectStringPostgresqlTest :
    AbstractVertxSqlClientPostgresqlTest<UserRepositoryVertxSqlClientPostgresqlSelectString>(),
    MutinySelectStringTest<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles, PostgresqlCompanies,
            UserRepositoryVertxSqlClientPostgresqlSelectString> {
    override fun instantiateRepository(sqlClient: PostgresqlMutinyVertxSqlClient) =
        UserRepositoryVertxSqlClientPostgresqlSelectString(sqlClient)
}

class UserRepositoryVertxSqlClientPostgresqlSelectString(sqlClient: MutinyVertxSqlClient) :
    MutinySelectStringRepository<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles, PostgresqlCompanies>(
        sqlClient,
        PostgresqlRoles,
        PostgresqlUsers,
        PostgresqlUserRoles,
        PostgresqlCompanies
    )
