/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mysql

import org.junit.jupiter.api.Tag
import org.ufoss.kotysa.*
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLocalTimeRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLocalTimeTest

@Tag("asyncer")
class R2dbcSelectLocalTimeMysqlTest : AbstractR2dbcMysqlTest<LocalTimeRepositoryMysqlSelect>(),
    ReactorSelectLocalTimeTest<MysqlLocalTimes, LocalTimeRepositoryMysqlSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MysqlReactorSqlClient, coSqlClient: MysqlCoroutinesSqlClient) =
        LocalTimeRepositoryMysqlSelect(sqlClient)
}

class LocalTimeRepositoryMysqlSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectLocalTimeRepository<MysqlLocalTimes>(
        sqlClient,
        MysqlLocalTimes
    )
