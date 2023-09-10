/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.oracle

import org.ufoss.kotysa.test.OracleRoles
import org.ufoss.kotysa.test.OracleUserRoles
import org.ufoss.kotysa.test.OracleUsers
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinyUpdateDeleteRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinyUpdateDeleteTest

class JdbcUpdateDeleteOracleTest : AbstractVertxSqlClientOracleTest<UserRepositoryVertxSqlClientOracleUpdateDelete>(),
    MutinyUpdateDeleteTest<OracleRoles, OracleUsers, OracleUserRoles, UserRepositoryVertxSqlClientOracleUpdateDelete> {
    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = UserRepositoryVertxSqlClientOracleUpdateDelete(sqlClient)
}

class UserRepositoryVertxSqlClientOracleUpdateDelete(sqlClient: MutinyVertxSqlClient) :
    MutinyUpdateDeleteRepository<OracleRoles, OracleUsers, OracleUserRoles>(
        sqlClient,
        OracleRoles,
        OracleUsers,
        OracleUserRoles
    )
