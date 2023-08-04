/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mysql

import org.ufoss.kotysa.MysqlCoroutinesSqlClient
import org.ufoss.kotysa.MysqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MysqlRoles
import org.ufoss.kotysa.test.MysqlUserRoles
import org.ufoss.kotysa.test.MysqlUsers
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectStringRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectStringTest

class R2dbcSelectStringMysqlTest : AbstractR2dbcMysqlTest<UserRepositoryR2dbcMysqlSelectString>(),
    ReactorSelectStringTest<MysqlRoles, MysqlUsers, MysqlUserRoles, UserRepositoryR2dbcMysqlSelectString,
            ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MysqlReactorSqlClient, coSqlClient: MysqlCoroutinesSqlClient) =
        UserRepositoryR2dbcMysqlSelectString(sqlClient)
}

class UserRepositoryR2dbcMysqlSelectString(sqlClient: ReactorSqlClient) :
    ReactorSelectStringRepository<MysqlRoles, MysqlUsers, MysqlUserRoles>(
        sqlClient,
        MysqlRoles,
        MysqlUsers,
        MysqlUserRoles
    )
