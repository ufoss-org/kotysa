/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mssql

import io.r2dbc.spi.Connection
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*

class R2dbcSelectGroupByMssqlTest : AbstractR2dbcMssqlTest<GroupByRepositoryMssqlSelect>() {
    override fun instantiateRepository(connection: Connection) = GroupByRepositoryMssqlSelect(connection)

    @Test
    fun `Verify selectCountCustomerGroupByCountry counts and group`() = runTest {
        assertThat(repository.selectCountCustomerGroupByCountry().toList())
                .hasSize(2)
                .containsExactly(Pair(1, customerFrance.country), Pair(2, customerUSA1.country))
    }
}

class GroupByRepositoryMssqlSelect(connection: Connection) : AbstractCustomerRepositoryR2dbcMssql(connection) {

    fun selectCountCustomerGroupByCountry() =
            (sqlClient selectCount MSSQL_CUSTOMER.id and MSSQL_CUSTOMER.country
                    from MSSQL_CUSTOMER
                    groupBy MSSQL_CUSTOMER.country
                    ).fetchAll()
}
