/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.postgresql

import org.ufoss.kotysa.PostgresqlCoroutinesSqlClient
import org.ufoss.kotysa.PostgresqlReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.repositories.reactor.ReactorGenericAllTypesRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorGenericAllTypesTest

class R2dbcGenericAllTypesPostgresqlTest : AbstractR2dbcPostgresqlTest<ReactorGenericAllTypesRepository>(),
    ReactorGenericAllTypesTest<ReactorTransaction> {
    override fun instantiateRepository(
        sqlClient: PostgresqlReactorSqlClient,
        coSqlClient: PostgresqlCoroutinesSqlClient,
    ) = ReactorGenericAllTypesRepository(sqlClient)
}
