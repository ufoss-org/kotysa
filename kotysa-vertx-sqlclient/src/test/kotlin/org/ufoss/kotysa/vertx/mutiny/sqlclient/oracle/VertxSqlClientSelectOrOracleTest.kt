/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.oracle

import org.ufoss.kotysa.test.OracleRoles
import org.ufoss.kotysa.test.OracleUserRoles
import org.ufoss.kotysa.test.OracleUsers
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectOrRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectOrTest

class VertxSqlClientSelectOrOracleTest : AbstractVertxSqlClientOracleTest<UserRepositoryJdbcOracleSelectOr>(),
    MutinySelectOrTest<OracleRoles, OracleUsers, OracleUserRoles, UserRepositoryJdbcOracleSelectOr> {
    override fun instantiateRepository(sqlClient: VertxSqlClient) = UserRepositoryJdbcOracleSelectOr(sqlClient)
}

class UserRepositoryJdbcOracleSelectOr(sqlClient: VertxSqlClient) :
    MutinySelectOrRepository<OracleRoles, OracleUsers, OracleUserRoles>(
        sqlClient,
        OracleRoles,
        OracleUsers,
        OracleUserRoles
    )
