/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mariadb

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.test.MariadbCustomers
import org.ufoss.kotysa.test.customerFrance
import org.ufoss.kotysa.test.customerUSA1
import org.ufoss.kotysa.test.customerUSA2

class R2dbcSelectOrderByMariadbTest : AbstractR2dbcMariadbTest<OrderByRepositoryMariadbSelect>() {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = OrderByRepositoryMariadbSelect(sqlClient)

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

class OrderByRepositoryMariadbSelect(private val sqlClient: R2dbcSqlClient) :
    AbstractCustomerRepositoryR2dbcMariadb(sqlClient) {

    fun selectCustomerOrderByAgeAsc() =
        (sqlClient selectFrom MariadbCustomers
                orderByAsc MariadbCustomers.age
                ).fetchAll()

    fun selectCustomerOrderByAgeAndIdAsc() =
        (sqlClient selectFrom MariadbCustomers
                orderByAsc MariadbCustomers.age andAsc MariadbCustomers.id
                ).fetchAll()
}
