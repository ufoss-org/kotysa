/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource

class R2dbcSelectOrderByPostgresqlTest : AbstractR2dbcPostgresqlTest<OrderByRepositoryPostgresqlSelect>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<OrderByRepositoryPostgresqlSelect>(resource)
    }

    override val repository: OrderByRepositoryPostgresqlSelect by lazy {
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

class OrderByRepositoryPostgresqlSelect(sqlClient: ReactorSqlClient) : AbstractCustomerRepositoryPostgresql(sqlClient) {

    fun selectCustomerOrderByAgeAsc() =
            (sqlClient selectFrom PostgresqlCustomers
                    orderByAsc PostgresqlCustomers.age
                    ).fetchAll()

    fun selectCustomerOrderByAgeAndIdAsc() =
            (sqlClient selectFrom PostgresqlCustomers
                    orderByAsc PostgresqlCustomers.age andAsc PostgresqlCustomers.id
                    ).fetchAll()
}
