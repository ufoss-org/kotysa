/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.oracle

import org.ufoss.kotysa.test.OracleRoles
import org.ufoss.kotysa.test.OracleUserRoles
import org.ufoss.kotysa.test.OracleUsers
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectStringRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectStringTest

class VertxSqlClientSelectStringOracleTest : AbstractVertxSqlClientOracleTest<UserRepositoryVertxSqlClientOracleSelectString>(),
    MutinySelectStringTest<OracleRoles, OracleUsers, OracleUserRoles, UserRepositoryVertxSqlClientOracleSelectString> {
    override fun instantiateRepository(sqlClient: VertxSqlClient) = UserRepositoryVertxSqlClientOracleSelectString(sqlClient)
}

class UserRepositoryVertxSqlClientOracleSelectString(sqlClient: VertxSqlClient) :
    MutinySelectStringRepository<OracleRoles, OracleUsers, OracleUserRoles>(
        sqlClient,
        OracleRoles,
        OracleUsers,
        OracleUserRoles
    )
