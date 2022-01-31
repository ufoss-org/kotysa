/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mssql

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.jdbc.JdbcSqlClient
import org.ufoss.kotysa.test.*

class JdbcSelectLimitOffsetMssqlTest : AbstractJdbcMssqlTest<LimitOffsetByRepositoryMssqlSelect>() {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = LimitOffsetByRepositoryMssqlSelect(sqlClient)

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
    fun `Verify selectAllLimitOffset throw exception`() {
        assertThatThrownBy { repository.selectAllLimitOffset() }
                .isInstanceOf(IllegalArgumentException::class.java)
                .hasMessage("Mssql offset or limit must have order by")
    }

    @Test
    fun `Verify selectAllOrderByIdLimitOffset returns customerUSA1`() {
        assertThat(repository.selectAllOrderByIdLimitOffset())
                .hasSize(2)
                .containsExactly(customerUSA1, customerUSA2)
    }
}

class LimitOffsetByRepositoryMssqlSelect(private val sqlClient: JdbcSqlClient) :
    AbstractCustomerRepositoryJdbcMssql(sqlClient) {

    fun selectAllOrderByIdOffset() =
            (sqlClient selectFrom MSSQL_CUSTOMER
                    orderByAsc MSSQL_CUSTOMER.id
                    offset 2
                    ).fetchAll()

    fun selectAllOrderByIdLimit() =
            (sqlClient selectFrom MSSQL_CUSTOMER
                    orderByAsc MSSQL_CUSTOMER.id
                    limit 1
                    ).fetchAll()

    fun selectAllLimitOffset() =
            (sqlClient selectFrom MSSQL_CUSTOMER
                    limit 1 offset 1
                    ).fetchAll()

    fun selectAllOrderByIdLimitOffset() =
            (sqlClient selectFrom MSSQL_CUSTOMER
                    orderByAsc MSSQL_CUSTOMER.id
                    limit 2 offset 1
                    ).fetchAll()
}
