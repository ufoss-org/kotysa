/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mssql

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.r2dbc.R2dbcSqlClient
import org.ufoss.kotysa.test.MSSQL_CUSTOMER
import org.ufoss.kotysa.test.customerFrance
import org.ufoss.kotysa.test.customerUSA1
import org.ufoss.kotysa.test.customerUSA2

class R2dbcSelectLimitOffsetMssqlTest : AbstractR2dbcMssqlTest<LimitOffsetByRepositoryMssqlSelect>() {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = LimitOffsetByRepositoryMssqlSelect(sqlClient)

    @Test
    fun `Verify selectAllOrderByIdOffset returns customerUSA2`() = runTest {
        assertThat(repository.selectAllOrderByIdOffset().toList())
            .hasSize(1)
            .containsExactly(customerUSA2)
    }

    @Test
    fun `Verify selectAllOrderByIdLimit returns customerUSA2`() = runTest {
        assertThat(repository.selectAllOrderByIdLimit().toList())
            .hasSize(1)
            .containsExactly(customerFrance)
    }

    @Test
    fun `Verify selectAllLimitOffset throw exception`() = runTest {
        assertThatThrownBy { repository.selectAllLimitOffset() }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Mssql offset or limit must have order by")
    }

    @Test
    fun `Verify selectAllOrderByIdLimitOffset returns customerUSA1`() = runTest {
        assertThat(repository.selectAllOrderByIdLimitOffset().toList())
            .hasSize(2)
            .containsExactly(customerUSA1, customerUSA2)
    }
}

class LimitOffsetByRepositoryMssqlSelect(private val sqlClient: R2dbcSqlClient) :
    AbstractCustomerRepositoryR2dbcMssql(sqlClient) {

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
