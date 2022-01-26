/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.spring.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.test.*

class R2dbcSelectGroupByH2Test : AbstractR2dbcH2Test<GroupByRepositoryH2Select>() {
    override val context = startContext<GroupByRepositoryH2Select>()
    override val repository = getContextRepository<GroupByRepositoryH2Select>()

    @Test
    fun `Verify selectCountCustomerGroupByCountry counts and group`() {
        assertThat(repository.selectCountCustomerGroupByCountry().toIterable())
                .hasSize(2)
                .containsExactly(Pair(1, customerFrance.country), Pair(2, customerUSA1.country))
    }
}

class GroupByRepositoryH2Select(sqlClient: ReactorSqlClient) : org.ufoss.kotysa.spring.r2dbc.h2.AbstractCustomerRepositoryH2(sqlClient) {

    fun selectCountCustomerGroupByCountry() =
            (sqlClient selectCount H2_CUSTOMER.id and H2_CUSTOMER.country
                    from H2_CUSTOMER
                    groupBy H2_CUSTOMER.country
                    ).fetchAll()
}
