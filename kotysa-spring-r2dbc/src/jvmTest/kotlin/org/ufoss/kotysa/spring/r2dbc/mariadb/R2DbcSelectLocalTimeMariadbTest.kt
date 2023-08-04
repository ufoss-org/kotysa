/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mariadb

import org.ufoss.kotysa.MariadbCoroutinesSqlClient
import org.ufoss.kotysa.MariadbReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MariadbLocalTimes
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLocalTimeRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLocalTimeTest

class R2dbcSelectLocalTimeMariadbTest : AbstractR2dbcMariadbTest<LocalTimeRepositoryMariadbSelect>(),
    ReactorSelectLocalTimeTest<MariadbLocalTimes, LocalTimeRepositoryMariadbSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MariadbReactorSqlClient, coSqlClient: MariadbCoroutinesSqlClient) =
        LocalTimeRepositoryMariadbSelect(sqlClient)
}

class LocalTimeRepositoryMariadbSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectLocalTimeRepository<MariadbLocalTimes>(
        sqlClient,
        MariadbLocalTimes
    )
