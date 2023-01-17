/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.oracle

import org.ufoss.kotysa.test.OracleRoles
import org.ufoss.kotysa.test.OracleUserRoles
import org.ufoss.kotysa.test.OracleUsers
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectDistinctRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectDistinctTest

class VertxSqlClientSelectDistinctOracleTest :
    AbstractVertxSqlClientOracleTest<UserRepositoryJdbcOracleSelectDistinct>(),
    MutinySelectDistinctTest<OracleRoles, OracleUsers, OracleUserRoles, UserRepositoryJdbcOracleSelectDistinct> {
    override fun instantiateRepository(sqlClient: VertxSqlClient) = UserRepositoryJdbcOracleSelectDistinct(sqlClient)
}

class UserRepositoryJdbcOracleSelectDistinct(sqlClient: VertxSqlClient) :
    MutinySelectDistinctRepository<OracleRoles, OracleUsers, OracleUserRoles>(
        sqlClient,
        OracleRoles,
        OracleUsers,
        OracleUserRoles
    )
