/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mssql

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource

class SpringJdbcSelectLimitOffsetMssqlTest : AbstractSpringJdbcMssqlTest<LimitOffsetByRepositoryMssqlSelect>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<LimitOffsetByRepositoryMssqlSelect>(resource)
    }

    override val repository: LimitOffsetByRepositoryMssqlSelect by lazy {
        getContextRepository()
    }

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

class LimitOffsetByRepositoryMssqlSelect(client: JdbcOperations) : AbstractCustomerRepositorySpringJdbcMssql(client) {

    fun selectAllOrderByIdOffset() =
            (sqlClient selectFrom MssqlCustomers
                    orderByAsc MssqlCustomers.id
                    offset 2
                    ).fetchAll()

    fun selectAllOrderByIdLimit() =
            (sqlClient selectFrom MssqlCustomers
                    orderByAsc MssqlCustomers.id
                    limit 1
                    ).fetchAll()

    fun selectAllLimitOffset() =
            (sqlClient selectFrom MssqlCustomers
                    limit 1 offset 1
                    ).fetchAll()

    fun selectAllOrderByIdLimitOffset() =
            (sqlClient selectFrom MssqlCustomers
                    orderByAsc MssqlCustomers.id
                    limit 2 offset 1
                    ).fetchAll()
}
