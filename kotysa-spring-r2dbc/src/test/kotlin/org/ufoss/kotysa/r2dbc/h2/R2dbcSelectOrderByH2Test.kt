/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.test.*

class R2dbcSelectOrderByH2Test : AbstractR2dbcH2Test<OrderByRepositoryH2Select>() {

    @BeforeAll
    fun beforeAll() {
        context = startContext<OrderByRepositoryH2Select>()
        repository = getContextRepository()
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

class OrderByRepositoryH2Select(sqlClient: ReactorSqlClient) : AbstractCustomerRepositoryH2(sqlClient) {

    fun selectCustomerOrderByAgeAsc() =
            (sqlClient selectFrom H2_CUSTOMER
                    orderByAsc H2_CUSTOMER.age
                    ).fetchAll()

    fun selectCustomerOrderByAgeAndIdAsc() =
            (sqlClient selectFrom H2_CUSTOMER
                    orderByAsc H2_CUSTOMER.age andAsc H2_CUSTOMER.id
                    ).fetchAll()
}
