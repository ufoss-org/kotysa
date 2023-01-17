/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import org.ufoss.kotysa.PostgresqlR2dbcSqlClient
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.PostgresqlLocalTimes
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalTimeRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalTimeTest


class R2dbcSelectLocalTimePostgresqlTest : AbstractR2dbcPostgresqlTest<LocalTimeRepositoryPostgresqlSelect>(),
    CoroutinesSelectLocalTimeTest<PostgresqlLocalTimes, LocalTimeRepositoryPostgresqlSelect, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: PostgresqlR2dbcSqlClient) =
        LocalTimeRepositoryPostgresqlSelect(sqlClient)
}

class LocalTimeRepositoryPostgresqlSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectLocalTimeRepository<PostgresqlLocalTimes>(sqlClient, PostgresqlLocalTimes)
