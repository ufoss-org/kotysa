/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mariadb

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MariadbCustomers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectOrderByRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectOrderByTest

class R2dbcSelectOrderByMariadbTest : AbstractR2dbcMariadbTest<OrderByRepositoryMariadbSelect>(),
    CoroutinesSelectOrderByTest<MariadbCustomers, OrderByRepositoryMariadbSelect, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = OrderByRepositoryMariadbSelect(sqlClient)
}

class OrderByRepositoryMariadbSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectOrderByRepository<MariadbCustomers>(sqlClient, MariadbCustomers)
