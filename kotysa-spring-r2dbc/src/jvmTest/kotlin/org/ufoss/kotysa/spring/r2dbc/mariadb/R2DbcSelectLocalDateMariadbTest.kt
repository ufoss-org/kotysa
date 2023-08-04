/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mariadb

import org.ufoss.kotysa.MariadbCoroutinesSqlClient
import org.ufoss.kotysa.MariadbReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MariadbLocalDates
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLocalDateRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLocalDateTest

class R2dbcSelectLocalDateMariadbTest : AbstractR2dbcMariadbTest<LocalDateRepositoryMariadbSelect>(),
    ReactorSelectLocalDateTest<MariadbLocalDates, LocalDateRepositoryMariadbSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MariadbReactorSqlClient, coSqlClient: MariadbCoroutinesSqlClient) =
        LocalDateRepositoryMariadbSelect(sqlClient)
}

class LocalDateRepositoryMariadbSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectLocalDateRepository<MariadbLocalDates>(sqlClient, MariadbLocalDates)
