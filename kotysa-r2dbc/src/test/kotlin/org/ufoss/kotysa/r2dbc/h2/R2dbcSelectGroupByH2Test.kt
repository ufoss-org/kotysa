/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import io.r2dbc.spi.Connection
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*

class R2dbcSelectGroupByH2Test : AbstractR2dbcH2Test<GroupByRepositoryH2Select>() {
    override fun instantiateRepository(connection: Connection) = GroupByRepositoryH2Select(connection)

    @Test
    fun `Verify selectCountCustomerGroupByCountry counts and group`() = runTest {
        assertThat(repository.selectCountCustomerGroupByCountry().toList())
                .hasSize(2)
                .containsExactly(Pair(1, customerFrance.country), Pair(2, customerUSA1.country))
    }
}

class GroupByRepositoryH2Select(connection: Connection) : AbstractCustomerRepositoryR2dbcH2(connection) {

    fun selectCountCustomerGroupByCountry() =
            (sqlClient selectCount H2_CUSTOMER.id and H2_CUSTOMER.country
                    from H2_CUSTOMER
                    groupBy H2_CUSTOMER.country
                    ).fetchAll()
}
