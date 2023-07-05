/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mysql

import org.junit.jupiter.api.Tag
import org.ufoss.kotysa.MysqlCoroutinesSqlClient
import org.ufoss.kotysa.MysqlReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MysqlKotlinxLocalTimes
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectKotlinxLocalTimeRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectKotlinxLocalTimeTest

@Tag("asyncer")
class R2DbcSelectKotlinxLocalTimeMysqlTest : AbstractR2dbcMysqlTest<KotlinxLocalTimeMysqlRepository>(),
    ReactorSelectKotlinxLocalTimeTest<MysqlKotlinxLocalTimes, KotlinxLocalTimeMysqlRepository, ReactorTransaction> {

    override fun instantiateRepository(sqlClient: MysqlReactorSqlClient, coSqlClient: MysqlCoroutinesSqlClient) =
        KotlinxLocalTimeMysqlRepository(sqlClient)
}

class KotlinxLocalTimeMysqlRepository(sqlClient: MysqlReactorSqlClient) :
    ReactorSelectKotlinxLocalTimeRepository<MysqlKotlinxLocalTimes>(sqlClient, MysqlKotlinxLocalTimes)
