/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mariadb

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource

class R2dbcSelectOrderByMariadbTest : AbstractR2dbcMariadbTest<OrderByRepositoryMariadbSelect>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<OrderByRepositoryMariadbSelect>(resource)
    }

    override val repository: OrderByRepositoryMariadbSelect by lazy {
        getContextRepository()
    }

    @Test
    fun `Verify selectCustomerOrderByAgeAsc returns all customers ordered by age ASC`() {
        assertThat(repository.selectCustomerOrderByAgeAsc().toIterable())
                .hasSize(3)
                .containsExactly(customerFrance, customerUSA2, customerUSA1)
    }

    @Test
    fun `Verify selectCustomerOrderByAgeAndIdAsc returns all customers ordered by age and id ASC`() {
        assertThat(repository.selectCustomerOrderByAgeAndIdAsc().toIterable())
                .hasSize(3)
                .containsExactly(customerFrance, customerUSA2, customerUSA1)
    }
}

class OrderByRepositoryMariadbSelect(sqlClient: ReactorSqlClient) : AbstractCustomerRepositoryMariadb(sqlClient) {

    fun selectCustomerOrderByAgeAsc() =
            (sqlClient selectFrom MARIADB_CUSTOMER
                    orderByAsc MARIADB_CUSTOMER.age
                    ).fetchAll()

    fun selectCustomerOrderByAgeAndIdAsc() =
            (sqlClient selectFrom MARIADB_CUSTOMER
                    orderByAsc MARIADB_CUSTOMER.age andAsc MARIADB_CUSTOMER.id
                    ).fetchAll()
}
