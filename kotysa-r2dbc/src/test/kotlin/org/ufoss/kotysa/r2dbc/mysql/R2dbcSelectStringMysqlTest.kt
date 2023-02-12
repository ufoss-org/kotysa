/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MysqlRoles
import org.ufoss.kotysa.test.MysqlUserRoles
import org.ufoss.kotysa.test.MysqlUsers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectStringRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectStringTest


class R2dbcSelectStringMysqlTest : AbstractR2dbcMysqlTest<UserRepositoryJdbcMysqlSelectString>(),
    CoroutinesSelectStringTest<MysqlRoles, MysqlUsers, MysqlUserRoles, UserRepositoryJdbcMysqlSelectString,
            R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = UserRepositoryJdbcMysqlSelectString(sqlClient)
}

class UserRepositoryJdbcMysqlSelectString(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectStringRepository<MysqlRoles, MysqlUsers, MysqlUserRoles>(
        sqlClient,
        MysqlRoles,
        MysqlUsers,
        MysqlUserRoles
    )
