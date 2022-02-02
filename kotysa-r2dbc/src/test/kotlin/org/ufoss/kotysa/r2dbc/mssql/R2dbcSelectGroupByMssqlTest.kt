/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mssql

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.r2dbc.R2dbcSqlClient
import org.ufoss.kotysa.test.MSSQL_CUSTOMER
import org.ufoss.kotysa.test.customerFrance
import org.ufoss.kotysa.test.customerUSA1

class R2dbcSelectGroupByMssqlTest : AbstractR2dbcMssqlTest<GroupByRepositoryMssqlSelect>() {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = GroupByRepositoryMssqlSelect(sqlClient)

    @Test
    fun `Verify selectCountCustomerGroupByCountry counts and group`() = runTest {
        assertThat(repository.selectCountCustomerGroupByCountry().toList())
            .hasSize(2)
            .containsExactly(Pair(1, customerFrance.country), Pair(2, customerUSA1.country))
    }
}

class GroupByRepositoryMssqlSelect(private val sqlClient: R2dbcSqlClient) :
    AbstractCustomerRepositoryR2dbcMssql(sqlClient) {

    fun selectCountCustomerGroupByCountry() =
        (sqlClient selectCount MSSQL_CUSTOMER.id and MSSQL_CUSTOMER.country
                from MSSQL_CUSTOMER
                groupBy MSSQL_CUSTOMER.country
                ).fetchAll()
}
