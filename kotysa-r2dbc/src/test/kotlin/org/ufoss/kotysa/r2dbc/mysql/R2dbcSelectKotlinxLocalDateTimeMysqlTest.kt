/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MysqlKotlinxLocalDateTimes
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectKotlinxLocalDateTimeRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectKotlinxLocalDateTimeTest


class R2dbcSelectKotlinxLocalDateTimeMysqlTest : AbstractR2dbcMysqlTest<KotlinxLocalDateTimeRepositoryMysqlSelect>(),
    CoroutinesSelectKotlinxLocalDateTimeTest<MysqlKotlinxLocalDateTimes, KotlinxLocalDateTimeRepositoryMysqlSelect,
            R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = KotlinxLocalDateTimeRepositoryMysqlSelect(sqlClient)
}

class KotlinxLocalDateTimeRepositoryMysqlSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectKotlinxLocalDateTimeRepository<MysqlKotlinxLocalDateTimes>(sqlClient, MysqlKotlinxLocalDateTimes)
