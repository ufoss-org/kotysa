/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mariadb

import org.ufoss.kotysa.MariadbCoroutinesSqlClient
import org.ufoss.kotysa.MariadbReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MariadbCustomers
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectOrderByRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectOrderByTest

class R2dbcSelectOrderByMariadbTest : AbstractR2dbcMariadbTest<OrderByRepositoryMariadbSelect>(),
    ReactorSelectOrderByTest<MariadbCustomers, OrderByRepositoryMariadbSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MariadbReactorSqlClient, coSqlClient: MariadbCoroutinesSqlClient) =
        OrderByRepositoryMariadbSelect(sqlClient)
}

class OrderByRepositoryMariadbSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectOrderByRepository<MariadbCustomers>(sqlClient, MariadbCustomers)
