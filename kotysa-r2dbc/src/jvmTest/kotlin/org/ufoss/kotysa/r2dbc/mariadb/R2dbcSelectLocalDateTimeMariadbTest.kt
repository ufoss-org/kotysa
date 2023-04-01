/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mariadb

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MariadbLocalDateTimes
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalDateTimeRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalDateTimeTest

class R2dbcSelectLocalDateTimeMariadbTest : AbstractR2dbcMariadbTest<LocalDateTimeRepositoryMariadbSelect>(),
    CoroutinesSelectLocalDateTimeTest<MariadbLocalDateTimes, LocalDateTimeRepositoryMariadbSelect, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = LocalDateTimeRepositoryMariadbSelect(sqlClient)
}

class LocalDateTimeRepositoryMariadbSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectLocalDateTimeRepository<MariadbLocalDateTimes>(sqlClient, MariadbLocalDateTimes)
