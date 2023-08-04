/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mariadb

import org.ufoss.kotysa.MariadbCoroutinesSqlClient
import org.ufoss.kotysa.MariadbReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MariadbLocalDateTimes
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLocalDateTimeRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLocalDateTimeTest

class R2dbcSelectLocalDateTimeMariadbTest : AbstractR2dbcMariadbTest<LocalDateTimeRepositoryMariadbSelect>(),
    ReactorSelectLocalDateTimeTest<MariadbLocalDateTimes, LocalDateTimeRepositoryMariadbSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MariadbReactorSqlClient, coSqlClient: MariadbCoroutinesSqlClient) =
        LocalDateTimeRepositoryMariadbSelect(sqlClient)
}

class LocalDateTimeRepositoryMariadbSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectLocalDateTimeRepository<MariadbLocalDateTimes>(
        sqlClient,
        MariadbLocalDateTimes
    )
