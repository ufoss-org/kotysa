/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.oracle

import org.ufoss.kotysa.test.OracleRoles
import org.ufoss.kotysa.test.OracleUserRoles
import org.ufoss.kotysa.test.OracleUsers
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySubQueryRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySubQueryTest

class VertxSqlClientSubQueryOracleTest : AbstractVertxSqlClientOracleTest<UserRepositoryVertxSqlClientOracleSubQuery>(),
    MutinySubQueryTest<OracleRoles, OracleUsers, OracleUserRoles, UserRepositoryVertxSqlClientOracleSubQuery> {
    override fun instantiateRepository(sqlClient: VertxSqlClient) =
        UserRepositoryVertxSqlClientOracleSubQuery(sqlClient)
}

class UserRepositoryVertxSqlClientOracleSubQuery(sqlClient: VertxSqlClient) :
    MutinySubQueryRepository<OracleRoles, OracleUsers, OracleUserRoles>(
        sqlClient,
        OracleRoles,
        OracleUsers,
        OracleUserRoles
    )
