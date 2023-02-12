/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MysqlRoles
import org.ufoss.kotysa.test.MysqlUserRoles
import org.ufoss.kotysa.test.MysqlUsers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesUpdateDeleteRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesUpdateDeleteTest

class R2dbcUpdateDeleteMysqlTest : AbstractR2dbcMysqlTest<UserRepositoryJdbcMysqlUpdateDelete>(),
    CoroutinesUpdateDeleteTest<MysqlRoles, MysqlUsers, MysqlUserRoles, UserRepositoryJdbcMysqlUpdateDelete,
            R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = UserRepositoryJdbcMysqlUpdateDelete(sqlClient)
}

class UserRepositoryJdbcMysqlUpdateDelete(sqlClient: R2dbcSqlClient) :
    CoroutinesUpdateDeleteRepository<MysqlRoles, MysqlUsers, MysqlUserRoles>(
        sqlClient,
        MysqlRoles,
        MysqlUsers,
        MysqlUserRoles
    )
