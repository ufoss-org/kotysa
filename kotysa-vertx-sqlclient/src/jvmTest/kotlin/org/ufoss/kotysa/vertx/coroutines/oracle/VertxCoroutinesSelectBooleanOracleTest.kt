/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.oracle

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.OracleRoles
import org.ufoss.kotysa.test.OracleUserRoles
import org.ufoss.kotysa.test.OracleUsers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectBooleanRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectBooleanTest


class VertxCoroutinesSelectBooleanOracleTest : AbstractVertxCoroutinesOracleTest<UserRepositoryJdbcOracleSelectBoolean>(),
    CoroutinesSelectBooleanTest<OracleRoles, OracleUsers, OracleUserRoles, UserRepositoryJdbcOracleSelectBoolean, Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = UserRepositoryJdbcOracleSelectBoolean(sqlClient)
}

class UserRepositoryJdbcOracleSelectBoolean(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectBooleanRepository<OracleRoles, OracleUsers, OracleUserRoles>(sqlClient, OracleRoles, OracleUsers, OracleUserRoles)
