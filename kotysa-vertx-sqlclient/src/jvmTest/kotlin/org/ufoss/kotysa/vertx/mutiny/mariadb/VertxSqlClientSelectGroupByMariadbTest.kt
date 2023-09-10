/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mariadb

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.MariadbCustomers
import org.ufoss.kotysa.test.customerFrance
import org.ufoss.kotysa.test.customerUSA1
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient

class VertxSqlClientSelectGroupByMariadbTest :
    AbstractVertxSqlClientMariadbTest<GroupByRepositoryMariadbSelect>() {

    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = GroupByRepositoryMariadbSelect(sqlClient)

    @Test
    fun `Verify selectCountCustomerGroupByCountry counts and group`() {
        assertThat(repository.selectCountCustomerGroupByCountry().await().indefinitely())
            .hasSize(2)
            .containsExactly(Pair(1, customerFrance.country), Pair(2, customerUSA1.country))
    }
}

class GroupByRepositoryMariadbSelect(sqlClient: MutinyVertxSqlClient) : AbstractCustomerRepositoryMariadb(sqlClient) {

    fun selectCountCustomerGroupByCountry() =
        (sqlClient selectCount MariadbCustomers.id and MariadbCustomers.country
                from MariadbCustomers
                groupBy MariadbCustomers.country
                ).fetchAll()
}
