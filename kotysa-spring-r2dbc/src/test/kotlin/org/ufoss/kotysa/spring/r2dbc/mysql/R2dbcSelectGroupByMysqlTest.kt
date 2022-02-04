/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mysql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.spring.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource

class R2dbcSelectGroupByMysqlTest : AbstractR2dbcMysqlTest<GroupByRepositoryMysqlSelect>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<GroupByRepositoryMysqlSelect>(resource)
    }

    override val repository: GroupByRepositoryMysqlSelect by lazy {
        getContextRepository()
    }

    @Test
    fun `Verify selectCountCustomerGroupByCountry counts and group`() {
        assertThat(repository.selectCountCustomerGroupByCountry().toIterable())
                .hasSize(2)
                .containsExactly(Pair(1, customerFrance.country), Pair(2, customerUSA1.country))
    }
}

class GroupByRepositoryMysqlSelect(sqlClient: ReactorSqlClient) : AbstractCustomerRepositoryMysql(sqlClient) {

    fun selectCountCustomerGroupByCountry() =
            (sqlClient selectCount MysqlCustomers.id and MysqlCustomers.country
                    from MysqlCustomers
                    groupBy MysqlCustomers.country
                    ).fetchAll()
}
