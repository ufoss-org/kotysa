/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.postgresql

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.PostgresqlCoroutinesSqlClient
import org.ufoss.kotysa.PostgresqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.PostgresqlInts
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectIntRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectIntTest

@Order(1)
class R2dbcSelectIntPostgresqlTest : AbstractR2dbcPostgresqlTest<ReactorSelectIntRepositoryPostgresqlSelect>(),
    ReactorSelectIntTest<PostgresqlInts, ReactorSelectIntRepositoryPostgresqlSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: PostgresqlReactorSqlClient, coSqlClient: PostgresqlCoroutinesSqlClient) =
        ReactorSelectIntRepositoryPostgresqlSelect(sqlClient)
}

class ReactorSelectIntRepositoryPostgresqlSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectIntRepository<PostgresqlInts>(sqlClient, PostgresqlInts)
