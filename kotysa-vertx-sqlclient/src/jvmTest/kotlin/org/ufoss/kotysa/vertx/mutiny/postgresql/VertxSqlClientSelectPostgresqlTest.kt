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
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectTest

class VertxSqlClientSelectPostgresqlTest : AbstractVertxSqlClientPostgresqlTest<UserRepositoryPostgresqlSelect>(),
    MutinySelectTest<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles, PostgresqlCompanies,
            UserRepositoryPostgresqlSelect> {

    override fun instantiateRepository(sqlClient: PostgresqlMutinyVertxSqlClient) =
        UserRepositoryPostgresqlSelect(sqlClient)
}

class UserRepositoryPostgresqlSelect(sqlClient: MutinyVertxSqlClient) :
    MutinySelectRepository<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles, PostgresqlCompanies>(
        sqlClient,
        PostgresqlRoles,
        PostgresqlUsers,
        PostgresqlUserRoles,
        PostgresqlCompanies
    )
