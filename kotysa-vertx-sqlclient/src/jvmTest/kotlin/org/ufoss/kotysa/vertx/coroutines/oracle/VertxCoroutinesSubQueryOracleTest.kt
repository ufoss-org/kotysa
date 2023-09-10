/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.oracle

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.OracleRoles
import org.ufoss.kotysa.test.OracleUserRoles
import org.ufoss.kotysa.test.OracleUsers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSubQueryRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSubQueryTest

class VertxCoroutinesSubQueryOracleTest : AbstractVertxCoroutinesOracleTest<UserRepositoryVertxCoroutinesOracleSubQuery>(),
    CoroutinesSubQueryTest<OracleRoles, OracleUsers, OracleUserRoles, UserRepositoryVertxCoroutinesOracleSubQuery,
            Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = UserRepositoryVertxCoroutinesOracleSubQuery(sqlClient)
}

class UserRepositoryVertxCoroutinesOracleSubQuery(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSubQueryRepository<OracleRoles, OracleUsers, OracleUserRoles>(
        sqlClient,
        OracleRoles,
        OracleUsers,
        OracleUserRoles
    )
