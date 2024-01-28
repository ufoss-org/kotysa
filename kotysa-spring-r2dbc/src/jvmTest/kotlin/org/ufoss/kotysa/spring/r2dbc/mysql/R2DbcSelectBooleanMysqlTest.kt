/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mysql

import org.ufoss.kotysa.MysqlCoroutinesSqlClient
import org.ufoss.kotysa.MysqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MysqlCompanies
import org.ufoss.kotysa.test.MysqlRoles
import org.ufoss.kotysa.test.MysqlUserRoles
import org.ufoss.kotysa.test.MysqlUsers
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectBooleanRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectBooleanTest

class R2dbcSelectBooleanMysqlTest : AbstractR2dbcMysqlTest<ReactorUserRepositoryMysqlSelectBoolean>(),
    ReactorSelectBooleanTest<MysqlRoles, MysqlUsers, MysqlUserRoles, MysqlCompanies,
            ReactorUserRepositoryMysqlSelectBoolean, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MysqlReactorSqlClient, coSqlClient: MysqlCoroutinesSqlClient) =
        ReactorUserRepositoryMysqlSelectBoolean(sqlClient)
}

class ReactorUserRepositoryMysqlSelectBoolean(sqlClient: ReactorSqlClient) :
    ReactorSelectBooleanRepository<MysqlRoles, MysqlUsers, MysqlUserRoles, MysqlCompanies>(
        sqlClient,
        MysqlRoles,
        MysqlUsers,
        MysqlUserRoles,
        MysqlCompanies
    )
