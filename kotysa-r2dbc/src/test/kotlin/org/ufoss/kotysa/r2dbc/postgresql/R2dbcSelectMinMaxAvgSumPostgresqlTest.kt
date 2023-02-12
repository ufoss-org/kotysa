/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import org.ufoss.kotysa.PostgresqlR2dbcSqlClient
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.PostgresqlCustomers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectMinMaxAvgSumRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectMinMaxAvgSumTest

class R2dbcSelectMinMaxAvgSumPostgresqlTest : AbstractR2dbcPostgresqlTest<MinMaxAvgSumRepositoryPostgresqlSelect>(),
    CoroutinesSelectMinMaxAvgSumTest<PostgresqlCustomers, MinMaxAvgSumRepositoryPostgresqlSelect, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: PostgresqlR2dbcSqlClient) =
        MinMaxAvgSumRepositoryPostgresqlSelect(sqlClient)
}

class MinMaxAvgSumRepositoryPostgresqlSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectMinMaxAvgSumRepository<PostgresqlCustomers>(sqlClient, PostgresqlCustomers)
