/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.oracle

import org.ufoss.kotysa.test.OracleRoles
import org.ufoss.kotysa.test.OracleUserRoles
import org.ufoss.kotysa.test.OracleUsers
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectTest

class VertxSqlClientSelectOracleTest : AbstractVertxSqlClientOracleTest<UserRepositoryVertxSqlClientOracleSelect>(),
    MutinySelectTest<OracleRoles, OracleUsers, OracleUserRoles, UserRepositoryVertxSqlClientOracleSelect> {
    override fun instantiateRepository(sqlClient: VertxSqlClient) = UserRepositoryVertxSqlClientOracleSelect(sqlClient)
}

class UserRepositoryVertxSqlClientOracleSelect(sqlClient: VertxSqlClient) :
    MutinySelectRepository<OracleRoles, OracleUsers, OracleUserRoles>(
        sqlClient,
        OracleRoles,
        OracleUsers,
        OracleUserRoles
    )
