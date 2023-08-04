/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.postgresql

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.PostgresqlCoroutinesSqlClient
import org.ufoss.kotysa.PostgresqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLongRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLongTest

@Order(2)
class R2dbcSelectLongPostgresqlTest : AbstractR2dbcPostgresqlTest<ReactorLongRepositoryPostgresqlSelect>(),
    ReactorSelectLongTest<PostgresqlLongs, ReactorLongRepositoryPostgresqlSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: PostgresqlReactorSqlClient, coSqlClient: PostgresqlCoroutinesSqlClient) =
        ReactorLongRepositoryPostgresqlSelect(sqlClient)
}

class ReactorLongRepositoryPostgresqlSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectLongRepository<PostgresqlLongs>(sqlClient, PostgresqlLongs)
