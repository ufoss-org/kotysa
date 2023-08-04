/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mysql

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.MysqlCoroutinesSqlClient
import org.ufoss.kotysa.MysqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLongRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLongTest

@Order(2)
class R2dbcSelectLongMysqlTest : AbstractR2dbcMysqlTest<ReactorLongRepositoryMysqlSelect>(),
    ReactorSelectLongTest<MysqlLongs, ReactorLongRepositoryMysqlSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MysqlReactorSqlClient, coSqlClient: MysqlCoroutinesSqlClient) =
        ReactorLongRepositoryMysqlSelect(sqlClient)
}

class ReactorLongRepositoryMysqlSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectLongRepository<MysqlLongs>(sqlClient, MysqlLongs)
