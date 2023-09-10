/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.oracle

import org.ufoss.kotysa.test.OracleRoles
import org.ufoss.kotysa.test.OracleUserRoles
import org.ufoss.kotysa.test.OracleUsers
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectTest

class VertxSqlClientSelectOracleTest : AbstractVertxSqlClientOracleTest<UserRepositoryVertxSqlClientOracleSelect>(),
    MutinySelectTest<OracleRoles, OracleUsers, OracleUserRoles, UserRepositoryVertxSqlClientOracleSelect> {
    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = UserRepositoryVertxSqlClientOracleSelect(sqlClient)
}

class UserRepositoryVertxSqlClientOracleSelect(sqlClient: MutinyVertxSqlClient) :
    MutinySelectRepository<OracleRoles, OracleUsers, OracleUserRoles>(
        sqlClient,
        OracleRoles,
        OracleUsers,
        OracleUserRoles
    )
