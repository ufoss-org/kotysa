/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mysql

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.MysqlCoroutinesSqlClient
import org.ufoss.kotysa.MysqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MysqlInts
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectIntRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectIntTest

@Order(1)
class R2dbcSelectIntMysqlTest : AbstractR2dbcMysqlTest<ReactorSelectIntRepositoryMysqlSelect>(),
    ReactorSelectIntTest<MysqlInts, ReactorSelectIntRepositoryMysqlSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MysqlReactorSqlClient, coSqlClient: MysqlCoroutinesSqlClient) =
        ReactorSelectIntRepositoryMysqlSelect(sqlClient)
}

class ReactorSelectIntRepositoryMysqlSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectIntRepository<MysqlInts>(sqlClient, MysqlInts)
