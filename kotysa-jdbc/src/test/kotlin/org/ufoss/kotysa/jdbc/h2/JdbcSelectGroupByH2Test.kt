/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.jdbc.JdbcSqlClient
import org.ufoss.kotysa.test.*

class JdbcSelectGroupByH2Test : AbstractJdbcH2Test<GroupByRepositoryH2Select>() {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = GroupByRepositoryH2Select(sqlClient)

    @Test
    fun `Verify selectCountCustomerGroupByCountry counts and group`() {
        assertThat(repository.selectCountCustomerGroupByCountry())
                .hasSize(2)
                .containsExactly(Pair(1, customerFrance.country), Pair(2, customerUSA1.country))
    }
}

class GroupByRepositoryH2Select(private val sqlClient: JdbcSqlClient) : AbstractCustomerRepositoryJdbcH2(sqlClient) {

    fun selectCountCustomerGroupByCountry() =
            (sqlClient selectCount H2Customers.id and H2Customers.country
                    from H2Customers
                    groupBy H2Customers.country
                    ).fetchAll()
}
