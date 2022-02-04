/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.jdbc.JdbcSqlClient
import org.ufoss.kotysa.test.*

class JdbcSelectLimitOffsetPostgresqlTest : AbstractJdbcPostgresqlTest<LimitOffsetRepositoryPostgresqlSelect>() {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = LimitOffsetRepositoryPostgresqlSelect(sqlClient)

    @Test
    fun `Verify selectAllOrderByIdOffset returns customerUSA2`() {
        assertThat(repository.selectAllOrderByIdOffset())
                .hasSize(1)
                .containsExactly(customerUSA2)
    }

    @Test
    fun `Verify selectAllOrderByIdLimit returns customerUSA2`() {
        assertThat(repository.selectAllOrderByIdLimit())
                .hasSize(1)
                .containsExactly(customerFrance)
    }

    @Test
    fun `Verify selectAllLimitOffset returns one result`() {
        assertThat(repository.selectAllLimitOffset())
                .hasSize(1)
    }

    @Test
    fun `Verify selectAllOrderByIdLimitOffset returns customerUSA1`() {
        assertThat(repository.selectAllOrderByIdLimitOffset())
                .hasSize(2)
                .containsExactly(customerUSA1, customerUSA2)
    }
}

class LimitOffsetRepositoryPostgresqlSelect(private val sqlClient: JdbcSqlClient) :
    AbstractCustomerRepositoryJdbcPostgresql(sqlClient) {

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
