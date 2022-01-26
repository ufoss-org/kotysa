/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import io.r2dbc.spi.Connection
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*

class R2dbcSelectOrderByH2Test : AbstractR2dbcH2Test<OrderByRepositoryH2Select>() {
    override fun instantiateRepository(connection: Connection) = OrderByRepositoryH2Select(connection)

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

class OrderByRepositoryH2Select(connection: Connection) : AbstractCustomerRepositoryR2dbcH2(connection) {

    fun selectCustomerOrderByAgeAsc() =
            (sqlClient selectFrom H2_CUSTOMER
                    orderByAsc H2_CUSTOMER.age
                    ).fetchAll()

    fun selectCustomerOrderByAgeAndIdAsc() =
            (sqlClient selectFrom H2_CUSTOMER
                    orderByAsc H2_CUSTOMER.age andAsc H2_CUSTOMER.id
                    ).fetchAll()
}
