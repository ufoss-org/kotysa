/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.postgresql

import org.ufoss.kotysa.PostgresqlCoroutinesSqlClient
import org.ufoss.kotysa.PostgresqlReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.PostgresqlDoubles
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectDoubleRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectDoubleTest

class R2DbcSelectDoublePostgresqlTest : AbstractR2dbcPostgresqlTest<DoublePostgresqlRepository>(),
    ReactorSelectDoubleTest<PostgresqlDoubles, DoublePostgresqlRepository,
            ReactorTransaction> {

    override fun instantiateRepository(
        sqlClient: PostgresqlReactorSqlClient,
        coSqlClient: PostgresqlCoroutinesSqlClient,
    ) = DoublePostgresqlRepository(sqlClient)
}

class DoublePostgresqlRepository(sqlClient: PostgresqlReactorSqlClient) :
    ReactorSelectDoubleRepository<PostgresqlDoubles>(sqlClient, PostgresqlDoubles)
