/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import io.r2dbc.spi.Connection
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*

class R2dbcSelectGroupByPostgresqlTest : AbstractR2dbcPostgresqlTest<GroupByRepositoryPostgresqlSelect>() {
    override fun instantiateRepository(connection: Connection) = GroupByRepositoryPostgresqlSelect(connection)

    @Test
    fun `Verify selectCountCustomerGroupByCountry counts and group`() = runTest {
        assertThat(repository.selectCountCustomerGroupByCountry().toList())
                .hasSize(2)
                .containsExactly(Pair(1, customerFrance.country), Pair(2, customerUSA1.country))
    }
}

class GroupByRepositoryPostgresqlSelect(connection: Connection) : AbstractCustomerRepositoryR2dbcPostgresql(connection) {

    fun selectCountCustomerGroupByCountry() =
            (sqlClient selectCount POSTGRESQL_CUSTOMER.id and POSTGRESQL_CUSTOMER.country
                    from POSTGRESQL_CUSTOMER
                    groupBy POSTGRESQL_CUSTOMER.country
                    ).fetchAll()
}
