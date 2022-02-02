/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.jdbc.JdbcSqlClient
import org.ufoss.kotysa.test.*

class JdbcSelectOrderByH2Test : AbstractJdbcH2Test<OrderByRepositoryH2Select>() {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = OrderByRepositoryH2Select(sqlClient)

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

class OrderByRepositoryH2Select(private val sqlClient: JdbcSqlClient) : AbstractCustomerRepositoryJdbcH2(sqlClient) {

    fun selectCustomerOrderByAgeAsc() =
            (sqlClient selectFrom H2_CUSTOMER
                    orderByAsc H2_CUSTOMER.age
                    ).fetchAll()

    fun selectCustomerOrderByAgeAndIdAsc() =
            (sqlClient selectFrom H2_CUSTOMER
                    orderByAsc H2_CUSTOMER.age andAsc H2_CUSTOMER.id
                    ).fetchAll()
}
