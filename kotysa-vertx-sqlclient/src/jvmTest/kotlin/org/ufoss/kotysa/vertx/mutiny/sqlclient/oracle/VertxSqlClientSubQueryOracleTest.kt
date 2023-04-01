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

class JdbcSubQueryOracleTest : AbstractVertxSqlClientOracleTest<UserRepositoryJdbcOracleSubQuery>(),
    MutinySubQueryTest<OracleRoles, OracleUsers, OracleUserRoles, UserRepositoryJdbcOracleSubQuery> {
    override fun instantiateRepository(sqlClient: VertxSqlClient) = UserRepositoryJdbcOracleSubQuery(sqlClient)
}

class UserRepositoryJdbcOracleSubQuery(sqlClient: VertxSqlClient) :
    MutinySubQueryRepository<OracleRoles, OracleUsers, OracleUserRoles>(
        sqlClient,
        OracleRoles,
        OracleUsers,
        OracleUserRoles
    )
