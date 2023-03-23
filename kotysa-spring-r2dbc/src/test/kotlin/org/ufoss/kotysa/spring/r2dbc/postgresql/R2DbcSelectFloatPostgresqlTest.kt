/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.postgresql

import org.ufoss.kotysa.PostgresqlCoroutinesSqlClient
import org.ufoss.kotysa.PostgresqlReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.PostgresqlFloats
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectFloatRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectFloatTest

class R2DbcSelectFloatPostgresqlTest : AbstractR2dbcPostgresqlTest<FloatPostgresqlRepository>(),
    ReactorSelectFloatTest<PostgresqlFloats, FloatPostgresqlRepository,
            ReactorTransaction> {

    override fun instantiateRepository(
        sqlClient: PostgresqlReactorSqlClient,
        coSqlClient: PostgresqlCoroutinesSqlClient,
    ) = FloatPostgresqlRepository(sqlClient)
}

class FloatPostgresqlRepository(sqlClient: PostgresqlReactorSqlClient) :
    ReactorSelectFloatRepository<PostgresqlFloats>(sqlClient, PostgresqlFloats)
