/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mysql

import org.ufoss.kotysa.MysqlCoroutinesSqlClient
import org.ufoss.kotysa.MysqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MysqlLocalDateTimes
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLocalDateTimeRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLocalDateTimeTest

class R2dbcSelectLocalDateTimeMysqlTest : AbstractR2dbcMysqlTest<LocalDateTimeRepositoryMysqlSelect>(),
    ReactorSelectLocalDateTimeTest<MysqlLocalDateTimes, LocalDateTimeRepositoryMysqlSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MysqlReactorSqlClient, coSqlClient: MysqlCoroutinesSqlClient) =
        LocalDateTimeRepositoryMysqlSelect(sqlClient)
}

class LocalDateTimeRepositoryMysqlSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectLocalDateTimeRepository<MysqlLocalDateTimes>(
        sqlClient,
        MysqlLocalDateTimes
    )
