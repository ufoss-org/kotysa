/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mssql

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.spring.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.test.MSSQL_CUSTOMER
import org.ufoss.kotysa.test.customerFrance
import org.ufoss.kotysa.test.customerUSA1
import org.ufoss.kotysa.test.customerUSA2
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource

class R2dbcSelectLimitOffsetMssqlTest : AbstractR2dbcMssqlTest<LimitOffsetRepositoryMssqlSelect>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<LimitOffsetRepositoryMssqlSelect>(resource)
    }

    override val repository: LimitOffsetRepositoryMssqlSelect by lazy {
        getContextRepository()
    }

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
    fun `Verify selectAllLimitOffset throw exception`() {
        assertThatThrownBy { repository.selectAllLimitOffset() }
                .isInstanceOf(IllegalArgumentException::class.java)
                .hasMessage("Mssql offset or limit must have order by")
    }

    @Test
    fun `Verify selectAllOrderByIdLimitOffset returns customerUSA1`() {
        assertThat(repository.selectAllOrderByIdLimitOffset().toIterable())
                .hasSize(2)
                .containsExactly(customerUSA1, customerUSA2)
    }
}

class LimitOffsetRepositoryMssqlSelect(sqlClient: ReactorSqlClient) : AbstractCustomerRepositoryMssql(sqlClient) {

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
