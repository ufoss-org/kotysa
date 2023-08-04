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
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectAndRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectAndTest

class R2DbcSelectAndMysqlTest : AbstractR2dbcMysqlTest<ReactorUserRepositoryMysqlSelectAnd>(),
    ReactorSelectAndTest<MysqlRoles, MysqlUsers, MysqlUserRoles, ReactorUserRepositoryMysqlSelectAnd,
            ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MysqlReactorSqlClient, coSqlClient: MysqlCoroutinesSqlClient) =
        ReactorUserRepositoryMysqlSelectAnd(sqlClient)
}

class ReactorUserRepositoryMysqlSelectAnd(sqlClient: ReactorSqlClient) :
    ReactorSelectAndRepository<MysqlRoles, MysqlUsers, MysqlUserRoles>(
        sqlClient,
        MysqlRoles,
        MysqlUsers,
        MysqlUserRoles
    )
