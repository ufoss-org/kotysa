/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.test.*

class R2dbcSelectLimitOffsetH2Test : AbstractR2dbcH2Test<LimitOffsetRepositoryH2Select>() {
    override val context = startContext<LimitOffsetRepositoryH2Select>()
    override val repository = getContextRepository<LimitOffsetRepositoryH2Select>()

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

class LimitOffsetRepositoryH2Select(sqlClient: ReactorSqlClient) : org.ufoss.kotysa.spring.r2dbc.h2.AbstractCustomerRepositoryH2(sqlClient) {

    fun selectAllOrderByIdOffset() =
            (sqlClient selectFrom H2Customers
                    orderByAsc H2Customers.id
                    offset 2
                    ).fetchAll()

    fun selectAllOrderByIdLimit() =
            (sqlClient selectFrom H2Customers
                    orderByAsc H2Customers.id
                    limit 1
                    ).fetchAll()

    fun selectAllLimitOffset() =
            (sqlClient selectFrom H2Customers
                    limit 1 offset 1
                    ).fetchAll()

    fun selectAllOrderByIdLimitOffset() =
            (sqlClient selectFrom H2Customers
                    orderByAsc H2Customers.id
                    limit 2 offset 1
                    ).fetchAll()
}
