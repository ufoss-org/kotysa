/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import org.ufoss.kotysa.PostgresqlR2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectTsvectorRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectTsvectorTest

class R2dbcSelectTsvectorPostgresqlTest : AbstractR2dbcPostgresqlTest<TsvectorRepositoryPostgresqlSelect>(),
    CoroutinesSelectTsvectorTest<TsvectorRepositoryPostgresqlSelect, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: PostgresqlR2dbcSqlClient) = TsvectorRepositoryPostgresqlSelect(sqlClient)
}

class TsvectorRepositoryPostgresqlSelect(sqlClient: PostgresqlR2dbcSqlClient) : CoroutinesSelectTsvectorRepository(sqlClient)
