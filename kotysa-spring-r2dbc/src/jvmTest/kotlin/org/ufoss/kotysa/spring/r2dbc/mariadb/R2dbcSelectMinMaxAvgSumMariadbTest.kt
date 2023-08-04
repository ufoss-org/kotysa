/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mariadb

import org.ufoss.kotysa.MariadbCoroutinesSqlClient
import org.ufoss.kotysa.MariadbReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MariadbCustomers
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectMinMaxAvgSumRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectMinMaxAvgSumTest

class R2dbcSelectMinMaxAvgSumMariadbTest : AbstractR2dbcMariadbTest<MinMaxAvgSumRepositoryMariadbSelect>(),
    ReactorSelectMinMaxAvgSumTest<MariadbCustomers, MinMaxAvgSumRepositoryMariadbSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MariadbReactorSqlClient, coSqlClient: MariadbCoroutinesSqlClient) =
        MinMaxAvgSumRepositoryMariadbSelect(sqlClient)
}

class MinMaxAvgSumRepositoryMariadbSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectMinMaxAvgSumRepository<MariadbCustomers>(sqlClient, MariadbCustomers)
