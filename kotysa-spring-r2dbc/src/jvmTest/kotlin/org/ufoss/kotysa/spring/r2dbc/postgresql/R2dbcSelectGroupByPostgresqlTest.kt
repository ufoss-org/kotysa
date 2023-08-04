/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.postgresql

import org.ufoss.kotysa.PostgresqlCoroutinesSqlClient
import org.ufoss.kotysa.PostgresqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.PostgresqlCustomers
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectGroupByRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectGroupByTest

class R2dbcSelectGroupByPostgresqlTest : AbstractR2dbcPostgresqlTest<GroupByRepositoryPostgresqlSelect>(),
    ReactorSelectGroupByTest<PostgresqlCustomers, GroupByRepositoryPostgresqlSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: PostgresqlReactorSqlClient, coSqlClient: PostgresqlCoroutinesSqlClient) =
        GroupByRepositoryPostgresqlSelect(sqlClient)
}

class GroupByRepositoryPostgresqlSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectGroupByRepository<PostgresqlCustomers>(sqlClient, PostgresqlCustomers)
