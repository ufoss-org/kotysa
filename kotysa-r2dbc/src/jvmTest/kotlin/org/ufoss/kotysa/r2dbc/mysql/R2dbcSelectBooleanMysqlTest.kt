/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MysqlCompanies
import org.ufoss.kotysa.test.MysqlRoles
import org.ufoss.kotysa.test.MysqlUserRoles
import org.ufoss.kotysa.test.MysqlUsers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectBooleanRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectBooleanTest


class R2dbcSelectBooleanMysqlTest : AbstractR2dbcMysqlTest<UserRepositoryJdbcMysqlSelectBoolean>(),
    CoroutinesSelectBooleanTest<MysqlRoles, MysqlUsers, MysqlUserRoles, MysqlCompanies,
            UserRepositoryJdbcMysqlSelectBoolean, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = UserRepositoryJdbcMysqlSelectBoolean(sqlClient)
}

class UserRepositoryJdbcMysqlSelectBoolean(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectBooleanRepository<MysqlRoles, MysqlUsers, MysqlUserRoles, MysqlCompanies>(
        sqlClient,
        MysqlRoles,
        MysqlUsers,
        MysqlUserRoles,
        MysqlCompanies
    )
