/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mariadb

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.test.*

class JdbcSelectGroupByMariadbTest : AbstractJdbcMariadbTest<GroupByRepositoryMariadbSelect>() {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = GroupByRepositoryMariadbSelect(sqlClient)

    @Test
    fun `Verify selectCountCustomerGroupByCountry counts and group`() {
        assertThat(repository.selectCountCustomerGroupByCountry())
                .hasSize(2)
                .containsExactly(Pair(1, customerFrance.country), Pair(2, customerUSA1.country))
    }
}

class GroupByRepositoryMariadbSelect(private val sqlClient: JdbcSqlClient) :
    AbstractCustomerRepositoryJdbcMariadb(sqlClient) {

    fun selectCountCustomerGroupByCountry() =
            (sqlClient selectCount MariadbCustomers.id and MariadbCustomers.country
                    from MariadbCustomers
                    groupBy MariadbCustomers.country
                    ).fetchAll()
}
