/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.postgresql

import org.ufoss.kotysa.PostgresqlCoroutinesSqlClient
import org.ufoss.kotysa.PostgresqlReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.PostgresqlInheriteds
import org.ufoss.kotysa.test.repositories.reactor.ReactorInheritanceRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorInheritanceTest

class R2DbcInheritancePostgresqlTest : AbstractR2dbcPostgresqlTest<ReactorInheritancePostgresqlRepository>(),
    ReactorInheritanceTest<PostgresqlInheriteds, ReactorInheritancePostgresqlRepository, ReactorTransaction> {
    override val table = PostgresqlInheriteds

    override fun instantiateRepository(
        sqlClient: PostgresqlReactorSqlClient,
        coSqlClient: PostgresqlCoroutinesSqlClient,
    ) = ReactorInheritancePostgresqlRepository(sqlClient)
}

class ReactorInheritancePostgresqlRepository(sqlClient: PostgresqlReactorSqlClient) :
    ReactorInheritanceRepository<PostgresqlInheriteds>(sqlClient, PostgresqlInheriteds)
