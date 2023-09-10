/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.postgresql

import org.ufoss.kotysa.test.PostgresqlRoles
import org.ufoss.kotysa.test.PostgresqlUserRoles
import org.ufoss.kotysa.test.PostgresqlUsers
import org.ufoss.kotysa.vertx.PostgresqlMutinyVertxSqlClient
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySubQueryRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySubQueryTest

class VertxSqlClientSubQueryPostgresqlTest : AbstractVertxSqlClientPostgresqlTest<UserRepositoryVertxSqlClientPostgresqlSubQuery>(),
    MutinySubQueryTest<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles, UserRepositoryVertxSqlClientPostgresqlSubQuery> {
    override fun instantiateRepository(sqlClient: PostgresqlMutinyVertxSqlClient) =
        UserRepositoryVertxSqlClientPostgresqlSubQuery(sqlClient)
}

class UserRepositoryVertxSqlClientPostgresqlSubQuery(sqlClient: MutinyVertxSqlClient) :
    MutinySubQueryRepository<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles>(
        sqlClient,
        PostgresqlRoles,
        PostgresqlUsers,
        PostgresqlUserRoles
    )
