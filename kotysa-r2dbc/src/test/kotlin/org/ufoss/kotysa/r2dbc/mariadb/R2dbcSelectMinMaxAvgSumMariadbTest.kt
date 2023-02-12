/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mariadb

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MariadbCustomers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectMinMaxAvgSumRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectMinMaxAvgSumTest

class R2dbcSelectMinMaxAvgSumMariadbTest : AbstractR2dbcMariadbTest<MinMaxAvgSumRepositoryMariadbSelect>(),
    CoroutinesSelectMinMaxAvgSumTest<MariadbCustomers, MinMaxAvgSumRepositoryMariadbSelect, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = MinMaxAvgSumRepositoryMariadbSelect(sqlClient)
}

class MinMaxAvgSumRepositoryMariadbSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectMinMaxAvgSumRepository<MariadbCustomers>(sqlClient, MariadbCustomers)
