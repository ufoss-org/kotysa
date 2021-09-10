/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource

class SpringJdbcSelectOrderByMysqlTest : AbstractSpringJdbcMysqlTest<OrderByRepositoryMysqlSelect>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<OrderByRepositoryMysqlSelect>(resource)
    }

    override val repository: OrderByRepositoryMysqlSelect by lazy {
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

class OrderByRepositoryMysqlSelect(client: JdbcOperations) : AbstractCustomerRepositorySpringJdbcMysql(client) {

    fun selectCustomerOrderByAgeAsc() =
            (sqlClient selectFrom MYSQL_CUSTOMER
                    orderByAsc MYSQL_CUSTOMER.age
                    ).fetchAll()

    fun selectCustomerOrderByAgeAndIdAsc() =
            (sqlClient selectFrom MYSQL_CUSTOMER
                    orderByAsc MYSQL_CUSTOMER.age andAsc MYSQL_CUSTOMER.id
                    ).fetchAll()
}
