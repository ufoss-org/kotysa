/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.postgresql

import org.ufoss.kotysa.test.PostgresqlRoles
import org.ufoss.kotysa.test.PostgresqlUserRoles
import org.ufoss.kotysa.test.PostgresqlUsers
import org.ufoss.kotysa.vertx.mutiny.sqlclient.PostgresqlVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectTest

class VertxSqlClientSelectPostgresqlTest : AbstractVertxSqlClientPostgresqlTest<UserRepositoryPostgresqlSelect>(),
    MutinySelectTest<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles, UserRepositoryPostgresqlSelect> {

    override fun instantiateRepository(sqlClient: PostgresqlVertxSqlClient) = UserRepositoryPostgresqlSelect(sqlClient)
}

class UserRepositoryPostgresqlSelect(sqlClient: VertxSqlClient) :
    MutinySelectRepository<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles>(
        sqlClient,
        PostgresqlRoles,
        PostgresqlUsers,
        PostgresqlUserRoles
    )
