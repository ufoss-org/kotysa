/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import org.junit.jupiter.api.Tag
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MysqlKotlinxLocalTimes
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectKotlinxLocalTimeRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectKotlinxLocalTimeTest

@Tag("asyncer")
class R2dbcSelectKotlinxLocalTimeMysqlTest : AbstractR2dbcMysqlTest<KotlinxLocalTimeRepositoryMysqlSelect>(),
    CoroutinesSelectKotlinxLocalTimeTest<MysqlKotlinxLocalTimes, KotlinxLocalTimeRepositoryMysqlSelect,
            R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = KotlinxLocalTimeRepositoryMysqlSelect(sqlClient)
}

class KotlinxLocalTimeRepositoryMysqlSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectKotlinxLocalTimeRepository<MysqlKotlinxLocalTimes>(sqlClient, MysqlKotlinxLocalTimes)
