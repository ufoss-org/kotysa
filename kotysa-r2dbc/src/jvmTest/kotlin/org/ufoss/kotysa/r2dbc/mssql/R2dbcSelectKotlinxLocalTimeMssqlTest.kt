/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mssql

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MssqlKotlinxLocalTimes
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectKotlinxLocalTimeRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectKotlinxLocalTimeTest

class R2dbcSelectKotlinxLocalTimeMssqlTest : AbstractR2dbcMssqlTest<KotlinxLocalTimeRepositoryMssqlSelect>(),
    CoroutinesSelectKotlinxLocalTimeTest<MssqlKotlinxLocalTimes, KotlinxLocalTimeRepositoryMssqlSelect, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = KotlinxLocalTimeRepositoryMssqlSelect(sqlClient)
}

class KotlinxLocalTimeRepositoryMssqlSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectKotlinxLocalTimeRepository<MssqlKotlinxLocalTimes>(sqlClient, MssqlKotlinxLocalTimes)
