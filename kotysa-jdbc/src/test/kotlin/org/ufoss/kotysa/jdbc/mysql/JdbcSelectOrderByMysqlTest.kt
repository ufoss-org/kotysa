/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mysql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import java.sql.Connection

class JdbcSelectOrderByMysqlTest : AbstractJdbcMysqlTest<OrderByRepositoryMysqlSelect>() {
    override fun instantiateRepository(connection: Connection) = OrderByRepositoryMysqlSelect(connection)

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

class OrderByRepositoryMysqlSelect(connection: Connection) : AbstractCustomerRepositoryJdbcMysql(connection) {

    fun selectCustomerOrderByAgeAsc() =
            (sqlClient selectFrom MYSQL_CUSTOMER
                    orderByAsc MYSQL_CUSTOMER.age
                    ).fetchAll()

    fun selectCustomerOrderByAgeAndIdAsc() =
            (sqlClient selectFrom MYSQL_CUSTOMER
                    orderByAsc MYSQL_CUSTOMER.age andAsc MYSQL_CUSTOMER.id
                    ).fetchAll()
}
