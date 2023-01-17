/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.oracle

import org.ufoss.kotysa.test.OracleRoles
import org.ufoss.kotysa.test.OracleUserRoles
import org.ufoss.kotysa.test.OracleUsers
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinyUpdateDeleteRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinyUpdateDeleteTest

class JdbcUpdateDeleteOracleTest : AbstractVertxSqlClientOracleTest<UserRepositoryJdbcOracleUpdateDelete>(),
    MutinyUpdateDeleteTest<OracleRoles, OracleUsers, OracleUserRoles, UserRepositoryJdbcOracleUpdateDelete> {
    override fun instantiateRepository(sqlClient: VertxSqlClient) = UserRepositoryJdbcOracleUpdateDelete(sqlClient)
}

class UserRepositoryJdbcOracleUpdateDelete(sqlClient: VertxSqlClient) :
    MutinyUpdateDeleteRepository<OracleRoles, OracleUsers, OracleUserRoles>(
        sqlClient,
        OracleRoles,
        OracleUsers,
        OracleUserRoles
    )
