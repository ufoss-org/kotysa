/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.PostgresqlKotlinxLocalTimes
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectKotlinxLocalTimeRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectKotlinxLocalTimeTest

class R2dbcSelectKotlinxLocalTimePostgresqlTest :
    AbstractR2dbcPostgresqlTest<KotlinxLocalTimeRepositoryPostgresqlSelect>(),
    CoroutinesSelectKotlinxLocalTimeTest<PostgresqlKotlinxLocalTimes, KotlinxLocalTimeRepositoryPostgresqlSelect,
            R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) =
        KotlinxLocalTimeRepositoryPostgresqlSelect(sqlClient)
}

class KotlinxLocalTimeRepositoryPostgresqlSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectKotlinxLocalTimeRepository<PostgresqlKotlinxLocalTimes>(sqlClient, PostgresqlKotlinxLocalTimes)
