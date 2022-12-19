/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import org.ufoss.kotysa.PostgresqlR2dbcSqlClient
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.PostgresqlFloats
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectFloatRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectFloatTest

class R2dbcSelectFloatPostgresqlTest :
    AbstractR2dbcPostgresqlTest<FloatRepositoryPostgresqlSelect>(),
    CoroutinesSelectFloatTest<PostgresqlFloats, FloatRepositoryPostgresqlSelect,
            R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: PostgresqlR2dbcSqlClient) =
        FloatRepositoryPostgresqlSelect(sqlClient)
}

class FloatRepositoryPostgresqlSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectFloatRepository<PostgresqlFloats>(sqlClient, PostgresqlFloats)
