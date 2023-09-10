/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.PostgresqlCustomers
import org.ufoss.kotysa.test.customerFrance
import org.ufoss.kotysa.test.customerUSA1
import org.ufoss.kotysa.test.customerUSA2
import org.ufoss.kotysa.vertx.PostgresqlMutinyVertxSqlClient
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient

class VertxSqlClientSelectOrderByPostgresqlTest :
    AbstractVertxSqlClientPostgresqlTest<OrderByRepositoryPostgresqlSelect>() {

    override fun instantiateRepository(sqlClient: PostgresqlMutinyVertxSqlClient) = OrderByRepositoryPostgresqlSelect(sqlClient)

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

class OrderByRepositoryPostgresqlSelect(sqlClient: MutinyVertxSqlClient) : AbstractCustomerRepositoryPostgresql(sqlClient) {

    fun selectCustomerOrderByAgeAsc() =
        (sqlClient selectFrom PostgresqlCustomers
                orderByAsc PostgresqlCustomers.age
                ).fetchAll()

    fun selectCustomerOrderByAgeAndIdAsc() =
        (sqlClient selectFrom PostgresqlCustomers
                orderByAsc PostgresqlCustomers.age andAsc PostgresqlCustomers.id
                ).fetchAll()
}
