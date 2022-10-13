/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mysql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource

class R2dbcSelectLimitOffsetMysqlTest : AbstractR2dbcMysqlTest<LimitOffsetRepositoryMysqlSelect>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<LimitOffsetRepositoryMysqlSelect>(resource)
    }

    override val repository: LimitOffsetRepositoryMysqlSelect by lazy {
        getContextRepository()
    }

    @Test
    fun `Verify selectAllOrderByIdOffset returns customerUSA2`() {
        assertThat(repository.selectAllOrderByIdOffset().toIterable())
                .hasSize(1)
                .containsExactly(customerUSA2)
    }

    @Test
    fun `Verify selectAllOrderByIdLimit returns customerUSA2`() {
        assertThat(repository.selectAllOrderByIdLimit().toIterable())
                .hasSize(1)
                .containsExactly(customerFrance)
    }

    @Test
    fun `Verify selectAllLimitOffset returns one result`() {
        assertThat(repository.selectAllLimitOffset().toIterable())
                .hasSize(1)
    }

    @Test
    fun `Verify selectAllOrderByIdLimitOffset returns customerUSA1`() {
        assertThat(repository.selectAllOrderByIdLimitOffset().toIterable())
                .hasSize(2)
                .containsExactly(customerUSA1, customerUSA2)
    }
}

class LimitOffsetRepositoryMysqlSelect(sqlClient: ReactorSqlClient) : AbstractCustomerRepositoryMysql(sqlClient) {

    fun selectAllOrderByIdOffset() =
            (sqlClient selectFrom MysqlCustomers
                    orderByAsc MysqlCustomers.id
                    offset 2
                    ).fetchAll()

    fun selectAllOrderByIdLimit() =
            (sqlClient selectFrom MysqlCustomers
                    orderByAsc MysqlCustomers.id
                    limit 1
                    ).fetchAll()

    fun selectAllLimitOffset() =
            (sqlClient selectFrom MysqlCustomers
                    limit 1 offset 1
                    ).fetchAll()

    fun selectAllOrderByIdLimitOffset() =
            (sqlClient selectFrom MysqlCustomers
                    orderByAsc MysqlCustomers.id
                    limit 2 offset 1
                    ).fetchAll()
}
