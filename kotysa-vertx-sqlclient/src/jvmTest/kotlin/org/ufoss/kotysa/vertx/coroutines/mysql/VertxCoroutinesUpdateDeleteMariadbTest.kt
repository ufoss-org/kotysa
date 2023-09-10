/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mysql

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MysqlRoles
import org.ufoss.kotysa.test.MysqlUserRoles
import org.ufoss.kotysa.test.MysqlUsers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesUpdateDeleteRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesUpdateDeleteTest

class VertxCoroutinesUpdateDeleteMysqlTest : AbstractVertxCoroutinesMysqlTest<UserRepositoryJdbcMysqlUpdateDelete>(),
    CoroutinesUpdateDeleteTest<MysqlRoles, MysqlUsers, MysqlUserRoles, UserRepositoryJdbcMysqlUpdateDelete,
            Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = UserRepositoryJdbcMysqlUpdateDelete(sqlClient)
}

class UserRepositoryJdbcMysqlUpdateDelete(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesUpdateDeleteRepository<MysqlRoles, MysqlUsers, MysqlUserRoles>(
        sqlClient,
        MysqlRoles,
        MysqlUsers,
        MysqlUserRoles
    )
