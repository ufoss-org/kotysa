/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.postgresql

import org.ufoss.kotysa.PostgresqlCoroutinesSqlClient
import org.ufoss.kotysa.PostgresqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.PostgresqlCustomers
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectOrderByRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectOrderByTest

class R2dbcSelectOrderByPostgresqlTest : AbstractR2dbcPostgresqlTest<OrderByRepositoryPostgresqlSelect>(),
    ReactorSelectOrderByTest<PostgresqlCustomers, OrderByRepositoryPostgresqlSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: PostgresqlReactorSqlClient, coSqlClient: PostgresqlCoroutinesSqlClient) =
        OrderByRepositoryPostgresqlSelect(sqlClient)
}

class OrderByRepositoryPostgresqlSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectOrderByRepository<PostgresqlCustomers>(sqlClient, PostgresqlCustomers)
