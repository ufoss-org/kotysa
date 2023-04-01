/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.mssql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.MssqlCustomers
import org.ufoss.kotysa.test.customerFrance
import org.ufoss.kotysa.test.customerUSA1
import org.ufoss.kotysa.test.customerUSA2
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient

class VertxSqlClientSelectOrderByMssqlTest :
    AbstractVertxSqlClientMssqlTest<OrderByRepositoryMssqlSelect>() {

    override fun instantiateRepository(sqlClient: VertxSqlClient) = OrderByRepositoryMssqlSelect(sqlClient)

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

class OrderByRepositoryMssqlSelect(sqlClient: VertxSqlClient) : AbstractCustomerRepositoryMssql(sqlClient) {

    fun selectCustomerOrderByAgeAsc() =
        (sqlClient selectFrom MssqlCustomers
                orderByAsc MssqlCustomers.age
                ).fetchAll()

    fun selectCustomerOrderByAgeAndIdAsc() =
        (sqlClient selectFrom MssqlCustomers
                orderByAsc MssqlCustomers.age andAsc MssqlCustomers.id
                ).fetchAll()
}
