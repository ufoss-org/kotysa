/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import org.ufoss.kotysa.PostgresqlR2dbcSqlClient
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.PostgresqlDoubles
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectDoubleRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectDoubleTest

class R2dbcSelectDoublePostgresqlTest :
    AbstractR2dbcPostgresqlTest<DoubleRepositoryPostgresqlSelect>(),
    CoroutinesSelectDoubleTest<PostgresqlDoubles, DoubleRepositoryPostgresqlSelect,
            R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: PostgresqlR2dbcSqlClient) =
        DoubleRepositoryPostgresqlSelect(sqlClient)
}

class DoubleRepositoryPostgresqlSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectDoubleRepository<PostgresqlDoubles>(sqlClient, PostgresqlDoubles)
