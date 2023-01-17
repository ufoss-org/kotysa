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

class VertxSqlClientSelectOracleTest : AbstractVertxSqlClientOracleTest<UserRepositoryJdbcOracleSelect>(),
    MutinySelectTest<OracleRoles, OracleUsers, OracleUserRoles, UserRepositoryJdbcOracleSelect> {
    override fun instantiateRepository(sqlClient: VertxSqlClient) = UserRepositoryJdbcOracleSelect(sqlClient)
}

class UserRepositoryJdbcOracleSelect(sqlClient: VertxSqlClient) :
    MutinySelectRepository<OracleRoles, OracleUsers, OracleUserRoles>(
        sqlClient,
        OracleRoles,
        OracleUsers,
        OracleUserRoles
    )
