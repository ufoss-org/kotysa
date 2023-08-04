/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.postgresql

import org.ufoss.kotysa.PostgresqlCoroutinesSqlClient
import org.ufoss.kotysa.PostgresqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.PostgresqlCustomers
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLimitOffsetRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLimitOffsetTest

class R2dbcSelectLimitOffsetPostgresqlTest : AbstractR2dbcPostgresqlTest<LimitOffsetRepositoryPostgresqlSelect>(),
    ReactorSelectLimitOffsetTest<PostgresqlCustomers, LimitOffsetRepositoryPostgresqlSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: PostgresqlReactorSqlClient, coSqlClient: PostgresqlCoroutinesSqlClient) =
        LimitOffsetRepositoryPostgresqlSelect(sqlClient)
}

class LimitOffsetRepositoryPostgresqlSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectLimitOffsetRepository<PostgresqlCustomers>(sqlClient, PostgresqlCustomers)
