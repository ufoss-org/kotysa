/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.oracle

import org.ufoss.kotysa.test.OracleRoles
import org.ufoss.kotysa.test.OracleUserRoles
import org.ufoss.kotysa.test.OracleUsers
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectAndRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectAndTest

class VertxSqlClientSelectAndOracleTest : AbstractVertxSqlClientOracleTest<UserRepositoryVertxSqlClientOracleSelectAnd>(),
    MutinySelectAndTest<OracleRoles, OracleUsers, OracleUserRoles, UserRepositoryVertxSqlClientOracleSelectAnd> {
    override fun instantiateRepository(sqlClient: VertxSqlClient) = UserRepositoryVertxSqlClientOracleSelectAnd(sqlClient)
}

class UserRepositoryVertxSqlClientOracleSelectAnd(sqlClient: VertxSqlClient) :
    MutinySelectAndRepository<OracleRoles, OracleUsers, OracleUserRoles>(
        sqlClient,
        OracleRoles,
        OracleUsers,
        OracleUserRoles
    )
