/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mariadb

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource

class SpringJdbcSelectLimitOffsetMariadbTest: AbstractSpringJdbcMariadbTest<LimitOffsetRepositoryMariadbSelect>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<LimitOffsetRepositoryMariadbSelect>(resource)
        repository = getContextRepository()
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

class LimitOffsetRepositoryMariadbSelect(client: JdbcOperations) : AbstractCustomerRepositorySpringJdbcMariadb(client) {

    fun selectAllOrderByIdOffset() =
            (sqlClient selectFrom MARIADB_CUSTOMER
                    orderByAsc MARIADB_CUSTOMER.id
                    offset 2
                    ).fetchAll()

    fun selectAllOrderByIdLimit() =
            (sqlClient selectFrom MARIADB_CUSTOMER
                    orderByAsc MARIADB_CUSTOMER.id
                    limit 1
                    ).fetchAll()

    fun selectAllLimitOffset() =
            (sqlClient selectFrom MARIADB_CUSTOMER
                    limit 1 offset 1
                    ).fetchAll()

    fun selectAllOrderByIdLimitOffset() =
            (sqlClient selectFrom MARIADB_CUSTOMER
                    orderByAsc MARIADB_CUSTOMER.id
                    limit 2 offset 1
                    ).fetchAll()
}
