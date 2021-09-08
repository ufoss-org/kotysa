/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mariadb

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import java.sql.Connection

class JdbcSelectOrderByMariadbTest : AbstractJdbcMariadbTest<OrderByRepositoryMariadbSelect>() {
    override fun instantiateRepository(connection: Connection) = OrderByRepositoryMariadbSelect(connection)

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

class OrderByRepositoryMariadbSelect(connection: Connection) : AbstractCustomerRepositoryJdbcMariadb(connection) {

    fun selectCustomerOrderByAgeAsc() =
            (sqlClient selectFrom MARIADB_CUSTOMER
                    orderByAsc MARIADB_CUSTOMER.age
                    ).fetchAll()

    fun selectCustomerOrderByAgeAndIdAsc() =
            (sqlClient selectFrom MARIADB_CUSTOMER
                    orderByAsc MARIADB_CUSTOMER.age andAsc MARIADB_CUSTOMER.id
                    ).fetchAll()
}
