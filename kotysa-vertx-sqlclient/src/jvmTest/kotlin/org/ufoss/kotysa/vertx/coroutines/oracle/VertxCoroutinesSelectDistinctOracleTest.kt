/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.oracle

import org.ufoss.kotysa.test.OracleCompanies
import org.ufoss.kotysa.test.OracleRoles
import org.ufoss.kotysa.test.OracleUserRoles
import org.ufoss.kotysa.test.OracleUsers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectDistinctRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectDistinctTest
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient

class VertxCoroutinesSelectDistinctOracleTest :
    AbstractVertxCoroutinesOracleTest<UserRepositoryJdbcOracleSelectDistinct>(),
    CoroutinesSelectDistinctTest<OracleRoles, OracleUsers, OracleUserRoles, OracleCompanies,
            UserRepositoryJdbcOracleSelectDistinct, Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) =
        UserRepositoryJdbcOracleSelectDistinct(sqlClient)
}

class UserRepositoryJdbcOracleSelectDistinct(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectDistinctRepository<OracleRoles, OracleUsers, OracleUserRoles, OracleCompanies>(
        sqlClient,
        OracleRoles,
        OracleUsers,
        OracleUserRoles,
        OracleCompanies
    )
