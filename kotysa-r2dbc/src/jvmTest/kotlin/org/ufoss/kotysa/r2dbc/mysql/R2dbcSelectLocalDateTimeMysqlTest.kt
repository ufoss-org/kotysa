/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MysqlLocalDateTimes
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalDateTimeRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalDateTimeTest

class R2dbcSelectLocalDateTimeMysqlTest : AbstractR2dbcMysqlTest<LocalDateTimeRepositoryMysqlSelect>(),
    CoroutinesSelectLocalDateTimeTest<MysqlLocalDateTimes, LocalDateTimeRepositoryMysqlSelect, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = LocalDateTimeRepositoryMysqlSelect(sqlClient)
}

class LocalDateTimeRepositoryMysqlSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectLocalDateTimeRepository<MysqlLocalDateTimes>(sqlClient, MysqlLocalDateTimes)
