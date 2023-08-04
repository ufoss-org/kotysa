/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mariadb

import org.ufoss.kotysa.MariadbCoroutinesSqlClient
import org.ufoss.kotysa.MariadbReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MariadbKotlinxLocalDateTimes
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectKotlinxLocalDateTimeRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectKotlinxLocalDateTimeTest

class R2dbcSelectKotlinxLocalDateTimeMariadbTest :
    AbstractR2dbcMariadbTest<KotlinxLocalDateTimeRepositoryMariadbSelect>(),
    ReactorSelectKotlinxLocalDateTimeTest<MariadbKotlinxLocalDateTimes, KotlinxLocalDateTimeRepositoryMariadbSelect,
            ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MariadbReactorSqlClient, coSqlClient: MariadbCoroutinesSqlClient) =
        KotlinxLocalDateTimeRepositoryMariadbSelect(sqlClient)
}

class KotlinxLocalDateTimeRepositoryMariadbSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectKotlinxLocalDateTimeRepository<MariadbKotlinxLocalDateTimes>(
        sqlClient,
        MariadbKotlinxLocalDateTimes
    )
