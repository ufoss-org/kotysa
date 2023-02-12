/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.oracle

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.OracleCustomers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectOrderByRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectOrderByTest

class R2dbcSelectOrderByOracleTest : AbstractR2dbcOracleTest<OrderByRepositoryOracleSelect>(),
    CoroutinesSelectOrderByTest<OracleCustomers, OrderByRepositoryOracleSelect, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = OrderByRepositoryOracleSelect(sqlClient)
}

class OrderByRepositoryOracleSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectOrderByRepository<OracleCustomers>(sqlClient, OracleCustomers)
