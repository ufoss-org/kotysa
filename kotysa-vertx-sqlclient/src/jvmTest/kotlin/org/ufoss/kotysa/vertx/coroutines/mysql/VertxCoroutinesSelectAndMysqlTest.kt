/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mysql

import org.ufoss.kotysa.test.MysqlCompanies
import org.ufoss.kotysa.test.MysqlRoles
import org.ufoss.kotysa.test.MysqlUserRoles
import org.ufoss.kotysa.test.MysqlUsers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectAndRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectAndTest
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient


class VertxCoroutinesSelectAndMysqlTest : AbstractVertxCoroutinesMysqlTest<UserRepositoryJdbcMysqlSelectAnd>(),
    CoroutinesSelectAndTest<MysqlRoles, MysqlUsers, MysqlUserRoles, MysqlCompanies, UserRepositoryJdbcMysqlSelectAnd,
            Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) =
        UserRepositoryJdbcMysqlSelectAnd(sqlClient)
}

class UserRepositoryJdbcMysqlSelectAnd(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectAndRepository<MysqlRoles, MysqlUsers, MysqlUserRoles, MysqlCompanies>(
        sqlClient,
        MysqlRoles,
        MysqlUsers,
        MysqlUserRoles,
        MysqlCompanies
    )
