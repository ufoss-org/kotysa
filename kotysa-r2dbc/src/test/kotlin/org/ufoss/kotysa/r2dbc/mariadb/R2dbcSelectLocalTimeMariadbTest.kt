/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mariadb

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MariadbLocalTimes
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalTimeRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalTimeTest


class R2dbcSelectLocalTimeMariadbTest : AbstractR2dbcMariadbTest<LocalTimeRepositoryMariadbSelect>(),
    CoroutinesSelectLocalTimeTest<MariadbLocalTimes, LocalTimeRepositoryMariadbSelect, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = LocalTimeRepositoryMariadbSelect(sqlClient)
}

class LocalTimeRepositoryMariadbSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectLocalTimeRepository<MariadbLocalTimes>(sqlClient, MariadbLocalTimes)
