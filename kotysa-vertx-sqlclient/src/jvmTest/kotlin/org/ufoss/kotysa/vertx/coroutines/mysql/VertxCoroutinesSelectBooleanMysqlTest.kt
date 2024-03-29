/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mysql

import org.ufoss.kotysa.test.MysqlCompanies
import org.ufoss.kotysa.test.MysqlRoles
import org.ufoss.kotysa.test.MysqlUserRoles
import org.ufoss.kotysa.test.MysqlUsers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectBooleanRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectBooleanTest
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient


class VertxCoroutinesSelectBooleanMysqlTest : AbstractVertxCoroutinesMysqlTest<UserRepositoryJdbcMysqlSelectBoolean>(),
    CoroutinesSelectBooleanTest<MysqlRoles, MysqlUsers, MysqlUserRoles, MysqlCompanies,
            UserRepositoryJdbcMysqlSelectBoolean, Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) =
        UserRepositoryJdbcMysqlSelectBoolean(sqlClient)
}

class UserRepositoryJdbcMysqlSelectBoolean(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectBooleanRepository<MysqlRoles, MysqlUsers, MysqlUserRoles, MysqlCompanies>(
        sqlClient,
        MysqlRoles,
        MysqlUsers,
        MysqlUserRoles,
        MysqlCompanies
    )
