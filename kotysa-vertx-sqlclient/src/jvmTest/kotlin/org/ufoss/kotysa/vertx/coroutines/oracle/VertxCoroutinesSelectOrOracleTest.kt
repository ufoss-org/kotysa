/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.oracle

import org.ufoss.kotysa.test.OracleCompanies
import org.ufoss.kotysa.test.OracleRoles
import org.ufoss.kotysa.test.OracleUserRoles
import org.ufoss.kotysa.test.OracleUsers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectOrRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectOrTest
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient

class VertxCoroutinesSelectOrOracleTest : AbstractVertxCoroutinesOracleTest<UserRepositoryJdbcOracleSelectOr>(),
    CoroutinesSelectOrTest<OracleRoles, OracleUsers, OracleUserRoles, OracleCompanies, UserRepositoryJdbcOracleSelectOr,
            Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) =
        UserRepositoryJdbcOracleSelectOr(sqlClient)
}

class UserRepositoryJdbcOracleSelectOr(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectOrRepository<OracleRoles, OracleUsers, OracleUserRoles, OracleCompanies>(
        sqlClient,
        OracleRoles,
        OracleUsers,
        OracleUserRoles,
        OracleCompanies
    )
