/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.postgresql

import org.ufoss.kotysa.test.PostgresqlRoles
import org.ufoss.kotysa.test.PostgresqlUserRoles
import org.ufoss.kotysa.test.PostgresqlUsers
import org.ufoss.kotysa.vertx.mutiny.sqlclient.PostgresqlVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySubQueryRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySubQueryTest

class VertxSqlClientSubQueryPostgresqlTest : AbstractVertxSqlClientPostgresqlTest<UserRepositoryVertxSqlClientPostgresqlSubQuery>(),
    MutinySubQueryTest<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles, UserRepositoryVertxSqlClientPostgresqlSubQuery> {
    override fun instantiateRepository(sqlClient: PostgresqlVertxSqlClient) =
        UserRepositoryVertxSqlClientPostgresqlSubQuery(sqlClient)
}

class UserRepositoryVertxSqlClientPostgresqlSubQuery(sqlClient: VertxSqlClient) :
    MutinySubQueryRepository<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles>(
        sqlClient,
        PostgresqlRoles,
        PostgresqlUsers,
        PostgresqlUserRoles
    )
