/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mariadb

import io.r2dbc.spi.Connection
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*

class R2dbcSelectOrderByMariadbTest : AbstractR2dbcMariadbTest<OrderByRepositoryMariadbSelect>() {
    override fun instantiateRepository(connection: Connection) = OrderByRepositoryMariadbSelect(connection)

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

class OrderByRepositoryMariadbSelect(connection: Connection) : AbstractCustomerRepositoryR2dbcMariadb(connection) {

    fun selectCustomerOrderByAgeAsc() =
            (sqlClient selectFrom MARIADB_CUSTOMER
                    orderByAsc MARIADB_CUSTOMER.age
                    ).fetchAll()

    fun selectCustomerOrderByAgeAndIdAsc() =
            (sqlClient selectFrom MARIADB_CUSTOMER
                    orderByAsc MARIADB_CUSTOMER.age andAsc MARIADB_CUSTOMER.id
                    ).fetchAll()
}
