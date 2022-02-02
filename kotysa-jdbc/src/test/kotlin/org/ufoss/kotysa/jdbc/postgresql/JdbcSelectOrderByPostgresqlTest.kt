/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.jdbc.JdbcSqlClient
import org.ufoss.kotysa.test.*

class JdbcSelectOrderByPostgresqlTest : AbstractJdbcPostgresqlTest<OrderByRepositoryPostgresqlSelect>() {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = OrderByRepositoryPostgresqlSelect(sqlClient)

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

class OrderByRepositoryPostgresqlSelect(private val sqlClient: JdbcSqlClient) :
    AbstractCustomerRepositoryJdbcPostgresql(sqlClient) {

    fun selectCustomerOrderByAgeAsc() =
            (sqlClient selectFrom POSTGRESQL_CUSTOMER
                    orderByAsc POSTGRESQL_CUSTOMER.age
                    ).fetchAll()

    fun selectCustomerOrderByAgeAndIdAsc() =
            (sqlClient selectFrom POSTGRESQL_CUSTOMER
                    orderByAsc POSTGRESQL_CUSTOMER.age andAsc POSTGRESQL_CUSTOMER.id
                    ).fetchAll()
}
