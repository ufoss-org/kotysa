/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.oracle

import org.ufoss.kotysa.test.OracleRoles
import org.ufoss.kotysa.test.OracleUserRoles
import org.ufoss.kotysa.test.OracleUsers
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySubQueryRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySubQueryTest

class VertxSqlClientSubQueryOracleTest : AbstractVertxSqlClientOracleTest<UserRepositoryVertxSqlClientOracleSubQuery>(),
    MutinySubQueryTest<OracleRoles, OracleUsers, OracleUserRoles, UserRepositoryVertxSqlClientOracleSubQuery> {
    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) =
        UserRepositoryVertxSqlClientOracleSubQuery(sqlClient)
}

class UserRepositoryVertxSqlClientOracleSubQuery(sqlClient: MutinyVertxSqlClient) :
    MutinySubQueryRepository<OracleRoles, OracleUsers, OracleUserRoles>(
        sqlClient,
        OracleRoles,
        OracleUsers,
        OracleUserRoles
    )
