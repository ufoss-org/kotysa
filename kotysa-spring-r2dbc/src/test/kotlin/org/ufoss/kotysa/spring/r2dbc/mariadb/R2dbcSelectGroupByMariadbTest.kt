/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mariadb

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.MariadbCoroutinesSqlClient
import org.ufoss.kotysa.MariadbReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.test.MariadbCustomers
import org.ufoss.kotysa.test.customerFrance
import org.ufoss.kotysa.test.customerUSA1

class R2dbcSelectGroupByMariadbTest : AbstractR2dbcMariadbTest<GroupByRepositoryMariadbSelect>() {

    override fun instantiateRepository(sqlClient: MariadbReactorSqlClient, coSqlClient: MariadbCoroutinesSqlClient) =
        GroupByRepositoryMariadbSelect(sqlClient)

    @Test
    fun `Verify selectCountCustomerGroupByCountry counts and group`() {
        assertThat(repository.selectCountCustomerGroupByCountry().toIterable())
            .hasSize(2)
            .containsExactly(Pair(1, customerFrance.country), Pair(2, customerUSA1.country))
    }
}

class GroupByRepositoryMariadbSelect(sqlClient: ReactorSqlClient) : AbstractCustomerRepositoryMariadb(sqlClient) {

    fun selectCountCustomerGroupByCountry() =
        (sqlClient selectCount MariadbCustomers.id and MariadbCustomers.country
                from MariadbCustomers
                groupBy MariadbCustomers.country
                ).fetchAll()
}
