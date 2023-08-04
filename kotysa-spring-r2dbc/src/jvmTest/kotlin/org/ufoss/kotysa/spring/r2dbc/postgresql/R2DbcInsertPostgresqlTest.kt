/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.postgresql

import org.junit.jupiter.api.Order
import org.springframework.dao.DataIntegrityViolationException
import org.ufoss.kotysa.PostgresqlCoroutinesSqlClient
import org.ufoss.kotysa.PostgresqlReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.PostgresqlCustomers
import org.ufoss.kotysa.test.PostgresqlInts
import org.ufoss.kotysa.test.PostgresqlLongs
import org.ufoss.kotysa.test.repositories.reactor.ReactorInsertRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorInsertTest

@Order(3)
class R2DbcInsertPostgresqlTest : AbstractR2dbcPostgresqlTest<PostgresqlInsertRepository>(),
    ReactorInsertTest<PostgresqlInts, PostgresqlLongs, PostgresqlCustomers, PostgresqlInsertRepository, ReactorTransaction> {

    override fun instantiateRepository(
        sqlClient: PostgresqlReactorSqlClient,
        coSqlClient: PostgresqlCoroutinesSqlClient,
    ) = PostgresqlInsertRepository(sqlClient)

    override val exceptionClass = DataIntegrityViolationException::class.java
}

class PostgresqlInsertRepository(sqlClient: PostgresqlReactorSqlClient) :
    ReactorInsertRepository<PostgresqlInts, PostgresqlLongs, PostgresqlCustomers>(
        sqlClient,
        PostgresqlInts,
        PostgresqlLongs,
        PostgresqlCustomers
    )
