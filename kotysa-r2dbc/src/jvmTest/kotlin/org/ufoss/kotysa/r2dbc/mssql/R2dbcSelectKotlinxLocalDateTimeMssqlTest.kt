/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mssql

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MssqlKotlinxLocalDateTimes
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectKotlinxLocalDateTimeRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectKotlinxLocalDateTimeTest


class R2dbcSelectKotlinxLocalDateTimeMssqlTest : AbstractR2dbcMssqlTest<KotlinxLocalDateTimeRepositoryMssqlSelect>(),
    CoroutinesSelectKotlinxLocalDateTimeTest<MssqlKotlinxLocalDateTimes, KotlinxLocalDateTimeRepositoryMssqlSelect,
            R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = KotlinxLocalDateTimeRepositoryMssqlSelect(sqlClient)
}

class KotlinxLocalDateTimeRepositoryMssqlSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectKotlinxLocalDateTimeRepository<MssqlKotlinxLocalDateTimes>(sqlClient, MssqlKotlinxLocalDateTimes)
