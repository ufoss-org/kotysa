/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.r2dbc.R2dbcSqlClient
import org.ufoss.kotysa.test.H2Customers
import org.ufoss.kotysa.test.customerFrance
import org.ufoss.kotysa.test.customerUSA1
import org.ufoss.kotysa.test.customerUSA2

class R2dbcSelectOrderByH2Test : AbstractR2dbcH2Test<OrderByRepositoryH2Select>() {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = OrderByRepositoryH2Select(sqlClient)

    @Test
    fun `Verify selectCustomerOrderByAgeAsc returns all customers ordered by age ASC`() = runTest {
        assertThat(repository.selectCustomerOrderByAgeAsc().toList())
            .hasSize(3)
            .containsExactly(customerFrance, customerUSA2, customerUSA1)
    }

    @Test
    fun `Verify selectCustomerOrderByAgeAndIdAsc returns all customers ordered by age and id ASC`() = runTest {
        assertThat(repository.selectCustomerOrderByAgeAndIdAsc().toList())
            .hasSize(3)
            .containsExactly(customerFrance, customerUSA2, customerUSA1)
    }
}

class OrderByRepositoryH2Select(private val sqlClient: R2dbcSqlClient) : AbstractCustomerRepositoryR2dbcH2(sqlClient) {

    fun selectCustomerOrderByAgeAsc() =
        (sqlClient selectFrom H2Customers
                orderByAsc H2Customers.age
                ).fetchAll()

    fun selectCustomerOrderByAgeAndIdAsc() =
        (sqlClient selectFrom H2Customers
                orderByAsc H2Customers.age andAsc H2Customers.id
                ).fetchAll()
}
