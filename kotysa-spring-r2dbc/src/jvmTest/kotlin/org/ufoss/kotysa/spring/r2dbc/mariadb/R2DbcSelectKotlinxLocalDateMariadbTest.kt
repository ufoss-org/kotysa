/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mariadb

import org.ufoss.kotysa.MariadbCoroutinesSqlClient
import org.ufoss.kotysa.MariadbReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MariadbKotlinxLocalDates
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectKotlinxLocalDateRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectKotlinxLocalDateTest

class R2dbcSelectKotlinxLocalDateMariadbTest :
    AbstractR2dbcMariadbTest<KotlinxLocalDateRepositoryMariadbSelect>(),
    ReactorSelectKotlinxLocalDateTest<MariadbKotlinxLocalDates, KotlinxLocalDateRepositoryMariadbSelect,
            ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MariadbReactorSqlClient, coSqlClient: MariadbCoroutinesSqlClient) =
        KotlinxLocalDateRepositoryMariadbSelect(sqlClient)
}

class KotlinxLocalDateRepositoryMariadbSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectKotlinxLocalDateRepository<MariadbKotlinxLocalDates>(sqlClient, MariadbKotlinxLocalDates)
