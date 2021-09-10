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

class SpringJdbcSelectOrderByMariadbTest : AbstractSpringJdbcMariadbTest<OrderByRepositoryMariadbSelect>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<OrderByRepositoryMariadbSelect>(resource)
    }

    override val repository: OrderByRepositoryMariadbSelect by lazy {
        getContextRepository()
    }

    @Test
    fun `Verify selectCustomerOrderByAgeAsc returns all customers ordered by age ASC`() {
        assertThat(repository.selectCustomerOrderByAgeAsc())
                .hasSize(3)
                .containsExactly(customerFrance, customerUSA2, customerUSA1)
    }

    @Test
    fun `Verify selectCustomerOrderByAgeAndIdAsc returns all customers ordered by age and id ASC`() {
        assertThat(repository.selectCustomerOrderByAgeAndIdAsc())
                .hasSize(3)
                .containsExactly(customerFrance, customerUSA2, customerUSA1)
    }
}

class OrderByRepositoryMariadbSelect(client: JdbcOperations) : AbstractCustomerRepositorySpringJdbcMariadb(client) {

    fun selectCustomerOrderByAgeAsc() =
            (sqlClient selectFrom MARIADB_CUSTOMER
                    orderByAsc MARIADB_CUSTOMER.age
                    ).fetchAll()

    fun selectCustomerOrderByAgeAndIdAsc() =
            (sqlClient selectFrom MARIADB_CUSTOMER
                    orderByAsc MARIADB_CUSTOMER.age andAsc MARIADB_CUSTOMER.id
                    ).fetchAll()
}
