/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.r2dbc.R2dbcSqlClient
import org.ufoss.kotysa.test.MYSQL_CUSTOMER
import org.ufoss.kotysa.test.customerFrance
import org.ufoss.kotysa.test.customerUSA1
import org.ufoss.kotysa.test.customerUSA2

class R2dbcSelectOrderByMysqlTest : AbstractR2dbcMysqlTest<OrderByRepositoryMysqlSelect>() {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = OrderByRepositoryMysqlSelect(sqlClient)

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

class OrderByRepositoryMysqlSelect(private val sqlClient: R2dbcSqlClient) :
    AbstractCustomerRepositoryR2dbcMysql(sqlClient) {

    fun selectCustomerOrderByAgeAsc() =
        (sqlClient selectFrom MYSQL_CUSTOMER
                orderByAsc MYSQL_CUSTOMER.age
                ).fetchAll()

    fun selectCustomerOrderByAgeAndIdAsc() =
        (sqlClient selectFrom MYSQL_CUSTOMER
                orderByAsc MYSQL_CUSTOMER.age andAsc MYSQL_CUSTOMER.id
                ).fetchAll()
}
