/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.oracle

import org.ufoss.kotysa.test.OracleRoles
import org.ufoss.kotysa.test.OracleUserRoles
import org.ufoss.kotysa.test.OracleUsers
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectOrRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectOrTest

class VertxSqlClientSelectOrOracleTest : AbstractVertxSqlClientOracleTest<UserRepositoryVertxSqlClientOracleSelectOr>(),
    MutinySelectOrTest<OracleRoles, OracleUsers, OracleUserRoles, UserRepositoryVertxSqlClientOracleSelectOr> {
    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = UserRepositoryVertxSqlClientOracleSelectOr(sqlClient)
}

class UserRepositoryVertxSqlClientOracleSelectOr(sqlClient: MutinyVertxSqlClient) :
    MutinySelectOrRepository<OracleRoles, OracleUsers, OracleUserRoles>(
        sqlClient,
        OracleRoles,
        OracleUsers,
        OracleUserRoles
    )
