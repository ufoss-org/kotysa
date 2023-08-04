/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.oracle

import org.ufoss.kotysa.test.OracleRoles
import org.ufoss.kotysa.test.OracleUserRoles
import org.ufoss.kotysa.test.OracleUsers
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectBooleanRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectBooleanTest

class VertxSqlClientSelectBooleanOracleTest : AbstractVertxSqlClientOracleTest<UserRepositoryVertxSqlClientOracleSelectBoolean>(),
    MutinySelectBooleanTest<OracleRoles, OracleUsers, OracleUserRoles, UserRepositoryVertxSqlClientOracleSelectBoolean> {
    override fun instantiateRepository(sqlClient: VertxSqlClient) = UserRepositoryVertxSqlClientOracleSelectBoolean(sqlClient)
}

class UserRepositoryVertxSqlClientOracleSelectBoolean(sqlClient: VertxSqlClient) :
    MutinySelectBooleanRepository<OracleRoles, OracleUsers, OracleUserRoles>(
        sqlClient,
        OracleRoles,
        OracleUsers,
        OracleUserRoles
    )
