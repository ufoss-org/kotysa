/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mysql

import org.ufoss.kotysa.MysqlCoroutinesSqlClient
import org.ufoss.kotysa.MysqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MysqlKotlinxLocalDateTimes
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectKotlinxLocalDateTimeRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectKotlinxLocalDateTimeTest

class R2dbcSelectKotlinxLocalDateTimeMysqlTest :
    AbstractR2dbcMysqlTest<KotlinxLocalDateTimeRepositoryMysqlSelect>(),
    ReactorSelectKotlinxLocalDateTimeTest<MysqlKotlinxLocalDateTimes, KotlinxLocalDateTimeRepositoryMysqlSelect,
            ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MysqlReactorSqlClient, coSqlClient: MysqlCoroutinesSqlClient) =
        KotlinxLocalDateTimeRepositoryMysqlSelect(sqlClient)
}

class KotlinxLocalDateTimeRepositoryMysqlSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectKotlinxLocalDateTimeRepository<MysqlKotlinxLocalDateTimes>(
        sqlClient,
        MysqlKotlinxLocalDateTimes
    )
