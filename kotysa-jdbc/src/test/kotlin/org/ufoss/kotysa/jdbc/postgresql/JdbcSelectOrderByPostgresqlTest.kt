/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import java.sql.Connection

class JdbcSelectOrderByPostgresqlTest : AbstractJdbcPostgresqlTest<OrderByRepositoryPostgresqlSelect>() {
    override fun instantiateRepository(connection: Connection) = OrderByRepositoryPostgresqlSelect(connection)

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

class OrderByRepositoryPostgresqlSelect(connection: Connection) : AbstractCustomerRepositoryJdbcPostgresql(connection) {

    fun selectCustomerOrderByAgeAsc() =
            (sqlClient selectFrom POSTGRESQL_CUSTOMER
                    orderByAsc POSTGRESQL_CUSTOMER.age
                    ).fetchAll()

    fun selectCustomerOrderByAgeAndIdAsc() =
            (sqlClient selectFrom POSTGRESQL_CUSTOMER
                    orderByAsc POSTGRESQL_CUSTOMER.age andAsc POSTGRESQL_CUSTOMER.id
                    ).fetchAll()
}
