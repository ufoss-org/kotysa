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
import org.ufoss.kotysa.test.repositories.reactor.ReactorUpdateDeleteRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorUpdateDeleteTest

class R2dbcUpdateDeleteMysqlTest : AbstractR2dbcMysqlTest<UserRepositoryR2dbcMysqlUpdateDelete>(),
    ReactorUpdateDeleteTest<MysqlRoles, MysqlUsers, MysqlUserRoles, UserRepositoryR2dbcMysqlUpdateDelete,
            ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MysqlReactorSqlClient, coSqlClient: MysqlCoroutinesSqlClient) =
        UserRepositoryR2dbcMysqlUpdateDelete(sqlClient)
}

class UserRepositoryR2dbcMysqlUpdateDelete(sqlClient: ReactorSqlClient) :
    ReactorUpdateDeleteRepository<MysqlRoles, MysqlUsers, MysqlUserRoles>(
        sqlClient,
        MysqlRoles,
        MysqlUsers,
        MysqlUserRoles
    )
