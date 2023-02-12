/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import org.ufoss.kotysa.PostgresqlR2dbcSqlClient
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.PostgresqlUuids
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectUuidRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectUuidTest

class R2dbcSelectUuidPostgresqlTest : AbstractR2dbcPostgresqlTest<UuidRepositoryPostgresqlSelect>(),
    CoroutinesSelectUuidTest<PostgresqlUuids, UuidRepositoryPostgresqlSelect, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: PostgresqlR2dbcSqlClient) = UuidRepositoryPostgresqlSelect(sqlClient)
}

class UuidRepositoryPostgresqlSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectUuidRepository<PostgresqlUuids>(sqlClient, PostgresqlUuids)
