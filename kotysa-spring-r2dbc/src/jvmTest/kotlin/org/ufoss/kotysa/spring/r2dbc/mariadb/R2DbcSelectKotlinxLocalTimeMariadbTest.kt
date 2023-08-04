/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mariadb

import org.ufoss.kotysa.MariadbCoroutinesSqlClient
import org.ufoss.kotysa.MariadbReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MariadbKotlinxLocalTimes
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectKotlinxLocalTimeRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectKotlinxLocalTimeTest

class R2dbcSelectKotlinxLocalTimeMariadbTest :
    AbstractR2dbcMariadbTest<KotlinxLocalTimeRepositoryMariadbSelect>(),
    ReactorSelectKotlinxLocalTimeTest<MariadbKotlinxLocalTimes, KotlinxLocalTimeRepositoryMariadbSelect,
            ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MariadbReactorSqlClient, coSqlClient: MariadbCoroutinesSqlClient) =
        KotlinxLocalTimeRepositoryMariadbSelect(sqlClient)
}

class KotlinxLocalTimeRepositoryMariadbSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectKotlinxLocalTimeRepository<MariadbKotlinxLocalTimes>(
        sqlClient,
        MariadbKotlinxLocalTimes
    )
