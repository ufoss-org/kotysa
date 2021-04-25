/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource

class R2dbcSelectGroupByPostgresqlTest : AbstractR2dbcPostgresqlTest<GroupByRepositoryPostgresqlSelect>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<GroupByRepositoryPostgresqlSelect>(resource)
        repository = getContextRepository()
    }

    @Test
    fun `Verify selectCountCustomerGroupByCountry counts and group`() {
        assertThat(repository.selectCountCustomerGroupByCountry().toIterable())
                .hasSize(2)
                .containsExactly(Pair(1, customerFrance.country), Pair(2, customerUSA1.country))
    }
}

class GroupByRepositoryPostgresqlSelect(sqlClient: ReactorSqlClient) : AbstractCustomerRepositoryPostgresql(sqlClient) {

    fun selectCountCustomerGroupByCountry() =
            (sqlClient selectCount POSTGRESQL_CUSTOMER.id and POSTGRESQL_CUSTOMER.country
                    from POSTGRESQL_CUSTOMER
                    groupBy POSTGRESQL_CUSTOMER.country
                    ).fetchAll()
}
