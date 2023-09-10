/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.oracle

import org.ufoss.kotysa.test.OracleRoles
import org.ufoss.kotysa.test.OracleUserRoles
import org.ufoss.kotysa.test.OracleUsers
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectDistinctRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectDistinctTest

class VertxSqlClientSelectDistinctOracleTest :
    AbstractVertxSqlClientOracleTest<UserRepositoryVertxSqlClientOracleSelectDistinct>(),
    MutinySelectDistinctTest<OracleRoles, OracleUsers, OracleUserRoles, UserRepositoryVertxSqlClientOracleSelectDistinct> {
    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = UserRepositoryVertxSqlClientOracleSelectDistinct(sqlClient)
}

class UserRepositoryVertxSqlClientOracleSelectDistinct(sqlClient: MutinyVertxSqlClient) :
    MutinySelectDistinctRepository<OracleRoles, OracleUsers, OracleUserRoles>(
        sqlClient,
        OracleRoles,
        OracleUsers,
        OracleUserRoles
    )
