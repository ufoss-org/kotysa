/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MysqlRoles
import org.ufoss.kotysa.test.MysqlUserRoles
import org.ufoss.kotysa.test.MysqlUsers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectAndRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectAndTest


class R2dbcSelectAndMysqlTest : AbstractR2dbcMysqlTest<UserRepositoryJdbcMysqlSelectAnd>(),
    CoroutinesSelectAndTest<MysqlRoles, MysqlUsers, MysqlUserRoles, UserRepositoryJdbcMysqlSelectAnd,
            R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = UserRepositoryJdbcMysqlSelectAnd(sqlClient)
}

class UserRepositoryJdbcMysqlSelectAnd(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectAndRepository<MysqlRoles, MysqlUsers, MysqlUserRoles>(
        sqlClient,
        MysqlRoles,
        MysqlUsers,
        MysqlUserRoles
    )
