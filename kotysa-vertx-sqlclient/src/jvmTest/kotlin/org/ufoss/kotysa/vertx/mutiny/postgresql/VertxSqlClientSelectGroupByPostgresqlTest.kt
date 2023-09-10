/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.PostgresqlCustomers
import org.ufoss.kotysa.test.customerFrance
import org.ufoss.kotysa.test.customerUSA1
import org.ufoss.kotysa.vertx.PostgresqlMutinyVertxSqlClient
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient

class VertxSqlClientSelectGroupByPostgresqlTest :
    AbstractVertxSqlClientPostgresqlTest<GroupByRepositoryPostgresqlSelect>() {

    override fun instantiateRepository(sqlClient: PostgresqlMutinyVertxSqlClient) = GroupByRepositoryPostgresqlSelect(sqlClient)

    @Test
    fun `Verify selectCountCustomerGroupByCountry counts and group`() {
        assertThat(repository.selectCountCustomerGroupByCountry().await().indefinitely())
            .hasSize(2)
            .containsExactly(Pair(1, customerFrance.country), Pair(2, customerUSA1.country))
    }
}

class GroupByRepositoryPostgresqlSelect(sqlClient: MutinyVertxSqlClient) : AbstractCustomerRepositoryPostgresql(sqlClient) {

    fun selectCountCustomerGroupByCountry() =
        (sqlClient selectCount PostgresqlCustomers.id and PostgresqlCustomers.country
                from PostgresqlCustomers
                groupBy PostgresqlCustomers.country
                ).fetchAll()
}
