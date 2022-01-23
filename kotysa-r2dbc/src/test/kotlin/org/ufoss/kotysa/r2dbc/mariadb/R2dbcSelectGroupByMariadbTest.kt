/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mariadb

import io.r2dbc.spi.Connection
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*

class R2dbcSelectGroupByMariadbTest : AbstractR2dbcMariadbTest<GroupByRepositoryMariadbSelect>() {
    override fun instantiateRepository(connection: Connection) = GroupByRepositoryMariadbSelect(connection)

    @Test
    fun `Verify selectCountCustomerGroupByCountry counts and group`() = runTest {
        assertThat(repository.selectCountCustomerGroupByCountry().toList())
                .hasSize(2)
                .containsExactly(Pair(1, customerFrance.country), Pair(2, customerUSA1.country))
    }
}

class GroupByRepositoryMariadbSelect(connection: Connection) : AbstractCustomerRepositoryR2dbcMariadb(connection) {

    fun selectCountCustomerGroupByCountry() =
            (sqlClient selectCount MARIADB_CUSTOMER.id and MARIADB_CUSTOMER.country
                    from MARIADB_CUSTOMER
                    groupBy MARIADB_CUSTOMER.country
                    ).fetchAll()
}
