/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.postgresql

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.r2dbc.BadSqlGrammarException
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

    @Test
    override fun `Verify selectToTsqueryBoth 'table create' throws Exception`() {
        assertThatThrownBy {
            repository.selectToTsqueryBoth("table create").blockLast()
        }.isInstanceOf(BadSqlGrammarException::class.java)
    }
}

class TsvectorPostgresqlRepository(sqlClient: PostgresqlReactorSqlClient) : ReactorSelectTsvectorRepository(sqlClient)
