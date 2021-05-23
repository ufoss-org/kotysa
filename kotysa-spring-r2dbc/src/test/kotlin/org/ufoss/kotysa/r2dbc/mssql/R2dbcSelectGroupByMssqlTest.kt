/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mssql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource

class R2dbcSelectGroupByMssqlTest : AbstractR2dbcMssqlTest<GroupByRepositoryMssqlSelect>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<GroupByRepositoryMssqlSelect>(resource)
        repository = getContextRepository()
    }

    @Test
    fun `Verify selectCountCustomerGroupByCountry counts and group`() {
        assertThat(repository.selectCountCustomerGroupByCountry().toIterable())
                .hasSize(2)
                .containsExactly(Pair(1, customerFrance.country), Pair(2, customerUSA1.country))
    }
}

class GroupByRepositoryMssqlSelect(sqlClient: ReactorSqlClient) : AbstractCustomerRepositoryMssql(sqlClient) {

    fun selectCountCustomerGroupByCountry() =
            (sqlClient selectCount MSSQL_CUSTOMER.id and MSSQL_CUSTOMER.country
                    from MSSQL_CUSTOMER
                    groupBy MSSQL_CUSTOMER.country
                    ).fetchAll()
}
