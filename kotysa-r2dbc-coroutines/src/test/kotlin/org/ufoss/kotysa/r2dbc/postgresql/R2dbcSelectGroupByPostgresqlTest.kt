/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import org.ufoss.kotysa.PostgresqlR2dbcSqlClient
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.PostgresqlCustomers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectGroupByRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectGroupByTest

class R2dbcSelectGroupByPostgresqlTest : AbstractR2dbcPostgresqlTest<GroupByRepositoryPostgresqlSelect>(),
    CoroutinesSelectGroupByTest<PostgresqlCustomers, GroupByRepositoryPostgresqlSelect, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: PostgresqlR2dbcSqlClient) =
        GroupByRepositoryPostgresqlSelect(sqlClient)
}

class GroupByRepositoryPostgresqlSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectGroupByRepository<PostgresqlCustomers>(sqlClient, PostgresqlCustomers)
