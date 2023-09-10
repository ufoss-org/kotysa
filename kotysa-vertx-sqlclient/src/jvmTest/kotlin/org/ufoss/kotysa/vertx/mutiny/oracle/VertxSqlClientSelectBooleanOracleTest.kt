/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.oracle

import org.ufoss.kotysa.test.OracleRoles
import org.ufoss.kotysa.test.OracleUserRoles
import org.ufoss.kotysa.test.OracleUsers
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectBooleanRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectBooleanTest

class VertxSqlClientSelectBooleanOracleTest : AbstractVertxSqlClientOracleTest<UserRepositoryVertxSqlClientOracleSelectBoolean>(),
    MutinySelectBooleanTest<OracleRoles, OracleUsers, OracleUserRoles, UserRepositoryVertxSqlClientOracleSelectBoolean> {
    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = UserRepositoryVertxSqlClientOracleSelectBoolean(sqlClient)
}

class UserRepositoryVertxSqlClientOracleSelectBoolean(sqlClient: MutinyVertxSqlClient) :
    MutinySelectBooleanRepository<OracleRoles, OracleUsers, OracleUserRoles>(
        sqlClient,
        OracleRoles,
        OracleUsers,
        OracleUserRoles
    )
