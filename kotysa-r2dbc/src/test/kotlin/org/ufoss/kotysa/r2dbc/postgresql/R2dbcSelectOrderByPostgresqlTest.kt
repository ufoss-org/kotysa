/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import org.ufoss.kotysa.PostgresqlR2dbcSqlClient
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.PostgresqlCustomers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectOrderByRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectOrderByTest

class R2dbcSelectOrderByPostgresqlTest : AbstractR2dbcPostgresqlTest<OrderByRepositoryPostgresqlSelect>(),
    CoroutinesSelectOrderByTest<PostgresqlCustomers, OrderByRepositoryPostgresqlSelect, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: PostgresqlR2dbcSqlClient) =
        OrderByRepositoryPostgresqlSelect(sqlClient)
}

class OrderByRepositoryPostgresqlSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectOrderByRepository<PostgresqlCustomers>(sqlClient, PostgresqlCustomers)
