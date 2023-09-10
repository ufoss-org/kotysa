/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.oracle

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.OracleRoles
import org.ufoss.kotysa.test.OracleUserRoles
import org.ufoss.kotysa.test.OracleUsers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectTest


class VertxCoroutinesSelectOracleTest : AbstractVertxCoroutinesOracleTest<UserRepositoryJdbcOracleSelect>(),
    CoroutinesSelectTest<OracleRoles, OracleUsers, OracleUserRoles, UserRepositoryJdbcOracleSelect, Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = UserRepositoryJdbcOracleSelect(sqlClient)
}

class UserRepositoryJdbcOracleSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectRepository<OracleRoles, OracleUsers, OracleUserRoles>(
        sqlClient,
        OracleRoles,
        OracleUsers,
        OracleUserRoles
    )
