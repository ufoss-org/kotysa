/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.oracle

import org.ufoss.kotysa.OracleCoroutinesSqlClient
import org.ufoss.kotysa.OracleReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.OracleCustomers
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectOrderByRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectOrderByTest

class R2dbcSelectOrderByOracleTest : AbstractR2dbcOracleTest<OrderByRepositoryOracleSelect>(),
    ReactorSelectOrderByTest<OracleCustomers, OrderByRepositoryOracleSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: OracleReactorSqlClient, coSqlClient: OracleCoroutinesSqlClient) =
        OrderByRepositoryOracleSelect(sqlClient)
}

class OrderByRepositoryOracleSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectOrderByRepository<OracleCustomers>(sqlClient, OracleCustomers)
