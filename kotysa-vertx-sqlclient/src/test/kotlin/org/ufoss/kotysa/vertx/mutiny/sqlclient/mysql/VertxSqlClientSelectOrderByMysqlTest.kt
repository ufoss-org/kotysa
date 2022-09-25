/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.mysql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.MysqlCustomers
import org.ufoss.kotysa.test.customerFrance
import org.ufoss.kotysa.test.customerUSA1
import org.ufoss.kotysa.test.customerUSA2
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient

class VertxSqlClientSelectOrderByMysqlTest :
    AbstractVertxSqlClientMysqlTest<OrderByRepositoryMysqlSelect>() {

    override fun instantiateRepository(sqlClient: VertxSqlClient) = OrderByRepositoryMysqlSelect(sqlClient)

    @Test
    fun `Verify selectCustomerOrderByAgeAsc returns all customers ordered by age ASC`() {
        assertThat(repository.selectCustomerOrderByAgeAsc().await().indefinitely())
            .hasSize(3)
            .containsExactly(customerFrance, customerUSA2, customerUSA1)
    }

    @Test
    fun `Verify selectCustomerOrderByAgeAndIdAsc returns all customers ordered by age and id ASC`() {
        assertThat(repository.selectCustomerOrderByAgeAndIdAsc().await().indefinitely())
            .hasSize(3)
            .containsExactly(customerFrance, customerUSA2, customerUSA1)
    }
}

class OrderByRepositoryMysqlSelect(sqlClient: VertxSqlClient) : AbstractCustomerRepositoryMysql(sqlClient) {

    fun selectCustomerOrderByAgeAsc() =
        (sqlClient selectFrom MysqlCustomers
                orderByAsc MysqlCustomers.age
                ).fetchAll()

    fun selectCustomerOrderByAgeAndIdAsc() =
        (sqlClient selectFrom MysqlCustomers
                orderByAsc MysqlCustomers.age andAsc MysqlCustomers.id
                ).fetchAll()
}
