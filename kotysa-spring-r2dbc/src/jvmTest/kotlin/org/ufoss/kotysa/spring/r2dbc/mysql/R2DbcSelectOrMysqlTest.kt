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
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectOrRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectOrTest

class R2dbcSelectOrMysqlTest : AbstractR2dbcMysqlTest<UserRepositoryR2dbcMysqlSelectOr>(),
    ReactorSelectOrTest<MysqlRoles, MysqlUsers, MysqlUserRoles, UserRepositoryR2dbcMysqlSelectOr,
            ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MysqlReactorSqlClient, coSqlClient: MysqlCoroutinesSqlClient) =
        UserRepositoryR2dbcMysqlSelectOr(sqlClient)
}

class UserRepositoryR2dbcMysqlSelectOr(sqlClient: ReactorSqlClient) :
    ReactorSelectOrRepository<MysqlRoles, MysqlUsers, MysqlUserRoles>(
        sqlClient,
        MysqlRoles,
        MysqlUsers,
        MysqlUserRoles
    )
