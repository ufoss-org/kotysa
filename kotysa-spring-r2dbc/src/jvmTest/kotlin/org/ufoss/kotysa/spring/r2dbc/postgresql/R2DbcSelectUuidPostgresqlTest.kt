/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.postgresql

import org.ufoss.kotysa.PostgresqlCoroutinesSqlClient
import org.ufoss.kotysa.PostgresqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.PostgresqlUuids
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectUuidRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectUuidTest

class R2DbcSelectUuidPostgresqlTest : AbstractR2dbcPostgresqlTest<UuidRepositoryPostgresqlSelect>(),
    ReactorSelectUuidTest<PostgresqlUuids, UuidRepositoryPostgresqlSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: PostgresqlReactorSqlClient, coSqlClient: PostgresqlCoroutinesSqlClient) =
        UuidRepositoryPostgresqlSelect(sqlClient)
}

class UuidRepositoryPostgresqlSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectUuidRepository<PostgresqlUuids>(sqlClient, PostgresqlUuids)
