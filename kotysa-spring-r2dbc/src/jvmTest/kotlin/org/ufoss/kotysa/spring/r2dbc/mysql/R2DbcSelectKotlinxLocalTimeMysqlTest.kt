/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mysql

import org.junit.jupiter.api.Tag
import org.ufoss.kotysa.*
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MysqlKotlinxLocalTimes
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectKotlinxLocalTimeRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectKotlinxLocalTimeTest

@Tag("asyncer")
class R2dbcSelectKotlinxLocalTimeMysqlTest :
    AbstractR2dbcMysqlTest<KotlinxLocalTimeRepositoryMysqlSelect>(),
    ReactorSelectKotlinxLocalTimeTest<MysqlKotlinxLocalTimes, KotlinxLocalTimeRepositoryMysqlSelect,
            ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MysqlReactorSqlClient, coSqlClient: MysqlCoroutinesSqlClient) =
        KotlinxLocalTimeRepositoryMysqlSelect(sqlClient)
}

class KotlinxLocalTimeRepositoryMysqlSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectKotlinxLocalTimeRepository<MysqlKotlinxLocalTimes>(
        sqlClient,
        MysqlKotlinxLocalTimes
    )
