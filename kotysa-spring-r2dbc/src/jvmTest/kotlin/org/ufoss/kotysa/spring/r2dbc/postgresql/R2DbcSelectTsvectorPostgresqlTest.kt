/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.postgresql

import org.ufoss.kotysa.PostgresqlCoroutinesSqlClient
import org.ufoss.kotysa.PostgresqlReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectTsvectorRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectTsvectorTest

class R2DbcSelectTsvectorPostgresqlTest : AbstractR2dbcPostgresqlTest<TsvectorPostgresqlRepository>(),
    ReactorSelectTsvectorTest<TsvectorPostgresqlRepository, ReactorTransaction> {

    override fun instantiateRepository(
        sqlClient: PostgresqlReactorSqlClient,
        coSqlClient: PostgresqlCoroutinesSqlClient,
    ) = TsvectorPostgresqlRepository(sqlClient)
}

class TsvectorPostgresqlRepository(sqlClient: PostgresqlReactorSqlClient) : ReactorSelectTsvectorRepository(sqlClient)
