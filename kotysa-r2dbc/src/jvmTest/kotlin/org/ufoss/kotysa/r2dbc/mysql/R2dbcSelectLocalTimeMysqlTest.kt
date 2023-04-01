/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import org.junit.jupiter.api.Tag
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalTimeRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalTimeTest

@Tag("miku")
class R2dbcSelectLocalTimeMysqlTest : AbstractR2dbcMysqlTest<LocalTimeRepositoryMysqlSelect>(),
    CoroutinesSelectLocalTimeTest<MysqlLocalTimes, LocalTimeRepositoryMysqlSelect, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = LocalTimeRepositoryMysqlSelect(sqlClient)
}

class LocalTimeRepositoryMysqlSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectLocalTimeRepository<MysqlLocalTimes>(sqlClient, MysqlLocalTimes)
