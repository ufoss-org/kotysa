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
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectIntAsIdentitiesRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectIntAsIdentitiesTest

@Order(1)
class R2DbcSelectIntAsIdentitiesPostgresqlTest :
    AbstractR2dbcPostgresqlTest<ReactorSelectIntAsIdentitiesRepositoryPostgresqlSelect>(),
    ReactorSelectIntAsIdentitiesTest<PostgresqlIntAsIdentities, ReactorSelectIntAsIdentitiesRepositoryPostgresqlSelect,
            ReactorTransaction> {
    override fun instantiateRepository(
        sqlClient: PostgresqlReactorSqlClient,
        coSqlClient: PostgresqlCoroutinesSqlClient
    ) = ReactorSelectIntAsIdentitiesRepositoryPostgresqlSelect(sqlClient)
}

class ReactorSelectIntAsIdentitiesRepositoryPostgresqlSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectIntAsIdentitiesRepository<PostgresqlIntAsIdentities>(sqlClient, PostgresqlIntAsIdentities)
