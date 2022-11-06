/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.test.H2Customers
import org.ufoss.kotysa.test.customerFrance
import org.ufoss.kotysa.test.customerUSA1

class R2dbcSelectGroupByH2Test : AbstractR2dbcH2Test<GroupByRepositoryH2Select>() {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = GroupByRepositoryH2Select(sqlClient)

    @Test
    fun `Verify selectCountCustomerGroupByCountry counts and group`() = runTest {
        assertThat(repository.selectCountCustomerGroupByCountry().toList())
            .hasSize(2)
            .containsExactly(Pair(1, customerFrance.country), Pair(2, customerUSA1.country))
    }
}

class GroupByRepositoryH2Select(private val sqlClient: R2dbcSqlClient) : AbstractCustomerRepositoryR2dbcH2(sqlClient) {

    fun selectCountCustomerGroupByCountry() =
        (sqlClient selectCount H2Customers.id and H2Customers.country
                from H2Customers
                groupBy H2Customers.country
                ).fetchAll()
}
