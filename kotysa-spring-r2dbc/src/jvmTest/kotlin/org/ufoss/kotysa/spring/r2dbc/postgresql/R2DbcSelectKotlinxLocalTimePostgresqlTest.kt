/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.postgresql

import org.ufoss.kotysa.PostgresqlCoroutinesSqlClient
import org.ufoss.kotysa.PostgresqlReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.PostgresqlKotlinxLocalTimes
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectKotlinxLocalTimeRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectKotlinxLocalTimeTest

class R2DbcSelectKotlinxLocalTimePostgresqlTest : AbstractR2dbcPostgresqlTest<KotlinxLocalTimePostgresqlRepository>(),
    ReactorSelectKotlinxLocalTimeTest<PostgresqlKotlinxLocalTimes, KotlinxLocalTimePostgresqlRepository,
            ReactorTransaction> {

    override fun instantiateRepository(
        sqlClient: PostgresqlReactorSqlClient,
        coSqlClient: PostgresqlCoroutinesSqlClient,
    ) = KotlinxLocalTimePostgresqlRepository(sqlClient)
}

class KotlinxLocalTimePostgresqlRepository(sqlClient: PostgresqlReactorSqlClient) :
    ReactorSelectKotlinxLocalTimeRepository<PostgresqlKotlinxLocalTimes>(sqlClient, PostgresqlKotlinxLocalTimes)
