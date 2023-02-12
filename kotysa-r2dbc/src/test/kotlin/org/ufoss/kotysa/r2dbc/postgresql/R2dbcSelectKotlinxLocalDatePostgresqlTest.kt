/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import org.ufoss.kotysa.PostgresqlR2dbcSqlClient
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.PostgresqlKotlinxLocalDates
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectKotlinxLocalDateRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectKotlinxLocalDateTest


class R2dbcSelectKotlinxLocalDatePostgresqlTest :
    AbstractR2dbcPostgresqlTest<KotlinxLocalDateRepositoryPostgresqlSelect>(),
    CoroutinesSelectKotlinxLocalDateTest<PostgresqlKotlinxLocalDates, KotlinxLocalDateRepositoryPostgresqlSelect, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: PostgresqlR2dbcSqlClient) =
        KotlinxLocalDateRepositoryPostgresqlSelect(sqlClient)
}

class KotlinxLocalDateRepositoryPostgresqlSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectKotlinxLocalDateRepository<PostgresqlKotlinxLocalDates>(sqlClient, PostgresqlKotlinxLocalDates)
