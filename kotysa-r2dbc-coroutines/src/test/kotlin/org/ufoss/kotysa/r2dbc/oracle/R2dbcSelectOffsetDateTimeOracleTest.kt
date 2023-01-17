/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.oracle

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.OracleOffsetDateTimes
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectOffsetDateTimeRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectOffsetDateTimeTest

class R2dbcSelectOffsetDateTimeOracleTest : AbstractR2dbcOracleTest<OffsetDateTimeRepositoryOracleSelect>(),
    CoroutinesSelectOffsetDateTimeTest<OracleOffsetDateTimes, OffsetDateTimeRepositoryOracleSelect, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = OffsetDateTimeRepositoryOracleSelect(sqlClient)
}

class OffsetDateTimeRepositoryOracleSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectOffsetDateTimeRepository<OracleOffsetDateTimes>(sqlClient, OracleOffsetDateTimes)
