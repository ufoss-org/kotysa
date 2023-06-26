/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import org.ufoss.kotysa.PostgresqlR2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesGenericAllTypesRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesGenericAllTypesTest

class R2dbcGenericAllTypesPostgresqlTest : AbstractR2dbcPostgresqlTest<CoroutinesGenericAllTypesRepository>(),
    CoroutinesGenericAllTypesTest<R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: PostgresqlR2dbcSqlClient) =
        CoroutinesGenericAllTypesRepository(sqlClient)
}
