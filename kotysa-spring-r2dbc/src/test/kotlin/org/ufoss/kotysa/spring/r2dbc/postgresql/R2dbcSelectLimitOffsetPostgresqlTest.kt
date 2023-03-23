/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.PostgresqlCoroutinesSqlClient
import org.ufoss.kotysa.PostgresqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.test.PostgresqlCustomers
import org.ufoss.kotysa.test.customerFrance
import org.ufoss.kotysa.test.customerUSA1
import org.ufoss.kotysa.test.customerUSA2

class R2dbcSelectLimitOffsetPostgresqlTest : AbstractR2dbcPostgresqlTest<LimitOffsetRepositoryPostgresqlSelect>() {

    override fun instantiateRepository(
        sqlClient: PostgresqlReactorSqlClient,
        coSqlClient: PostgresqlCoroutinesSqlClient,
    ) = LimitOffsetRepositoryPostgresqlSelect(sqlClient)

    @Test
    fun `Verify selectAllOrderByIdOffset returns customerUSA2`() {
        assertThat(repository.selectAllOrderByIdOffset().toIterable())
            .hasSize(1)
            .containsExactly(customerUSA2)
    }

    @Test
    fun `Verify selectAllOrderByIdLimit returns customerUSA2`() {
        assertThat(repository.selectAllOrderByIdLimit().toIterable())
            .hasSize(1)
            .containsExactly(customerFrance)
    }

    @Test
    fun `Verify selectAllLimitOffset returns one result`() {
        assertThat(repository.selectAllLimitOffset().toIterable())
            .hasSize(1)
    }

    @Test
    fun `Verify selectAllOrderByIdLimitOffset returns customerUSA1`() {
        assertThat(repository.selectAllOrderByIdLimitOffset().toIterable())
            .hasSize(2)
            .containsExactly(customerUSA1, customerUSA2)
    }
}

class LimitOffsetRepositoryPostgresqlSelect(sqlClient: ReactorSqlClient) :
    AbstractCustomerRepositoryPostgresql(sqlClient) {

    fun selectAllOrderByIdOffset() =
        (sqlClient selectFrom PostgresqlCustomers
                orderByAsc PostgresqlCustomers.id
                offset 2
                ).fetchAll()

    fun selectAllOrderByIdLimit() =
        (sqlClient selectFrom PostgresqlCustomers
                orderByAsc PostgresqlCustomers.id
                limit 1
                ).fetchAll()

    fun selectAllLimitOffset() =
        (sqlClient selectFrom PostgresqlCustomers
                limit 1 offset 1
                ).fetchAll()

    fun selectAllOrderByIdLimitOffset() =
        (sqlClient selectFrom PostgresqlCustomers
                orderByAsc PostgresqlCustomers.id
                limit 2 offset 1
                ).fetchAll()
}
