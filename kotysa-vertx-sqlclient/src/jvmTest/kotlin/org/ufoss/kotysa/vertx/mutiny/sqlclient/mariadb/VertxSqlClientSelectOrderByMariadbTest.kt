/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.mariadb

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.MariadbCustomers
import org.ufoss.kotysa.test.customerFrance
import org.ufoss.kotysa.test.customerUSA1
import org.ufoss.kotysa.test.customerUSA2
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient

class VertxSqlClientSelectOrderByMariadbTest :
    AbstractVertxSqlClientMariadbTest<OrderByRepositoryMariadbSelect>() {

    override fun instantiateRepository(sqlClient: VertxSqlClient) = OrderByRepositoryMariadbSelect(sqlClient)

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

class OrderByRepositoryMariadbSelect(sqlClient: VertxSqlClient) : AbstractCustomerRepositoryMariadb(sqlClient) {

    fun selectCustomerOrderByAgeAsc() =
        (sqlClient selectFrom MariadbCustomers
                orderByAsc MariadbCustomers.age
                ).fetchAll()

    fun selectCustomerOrderByAgeAndIdAsc() =
        (sqlClient selectFrom MariadbCustomers
                orderByAsc MariadbCustomers.age andAsc MariadbCustomers.id
                ).fetchAll()
}
