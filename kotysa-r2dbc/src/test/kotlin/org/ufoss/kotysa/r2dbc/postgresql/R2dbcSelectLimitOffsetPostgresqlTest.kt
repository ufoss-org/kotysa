/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.r2dbc.R2dbcSqlClient
import org.ufoss.kotysa.test.POSTGRESQL_CUSTOMER
import org.ufoss.kotysa.test.customerFrance
import org.ufoss.kotysa.test.customerUSA1
import org.ufoss.kotysa.test.customerUSA2

class R2dbcSelectLimitOffsetPostgresqlTest : AbstractR2dbcPostgresqlTest<LimitOffsetRepositoryPostgresqlSelect>() {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = LimitOffsetRepositoryPostgresqlSelect(sqlClient)

    @Test
    fun `Verify selectAllOrderByIdOffset returns customerUSA2`() = runTest {
        assertThat(repository.selectAllOrderByIdOffset().toList())
            .hasSize(1)
            .containsExactly(customerUSA2)
    }

    @Test
    fun `Verify selectAllOrderByIdLimit returns customerUSA2`() = runTest {
        assertThat(repository.selectAllOrderByIdLimit().toList())
            .hasSize(1)
            .containsExactly(customerFrance)
    }

    @Test
    fun `Verify selectAllLimitOffset returns one result`() = runTest {
        assertThat(repository.selectAllLimitOffset().toList())
            .hasSize(1)
    }

    @Test
    fun `Verify selectAllOrderByIdLimitOffset returns customerUSA1`() = runTest {
        assertThat(repository.selectAllOrderByIdLimitOffset().toList())
            .hasSize(2)
            .containsExactly(customerUSA1, customerUSA2)
    }
}

class LimitOffsetRepositoryPostgresqlSelect(private val sqlClient: R2dbcSqlClient) :
    AbstractCustomerRepositoryR2dbcPostgresql(sqlClient) {

    fun selectAllOrderByIdOffset() =
        (sqlClient selectFrom POSTGRESQL_CUSTOMER
                orderByAsc POSTGRESQL_CUSTOMER.id
                offset 2
                ).fetchAll()

    fun selectAllOrderByIdLimit() =
        (sqlClient selectFrom POSTGRESQL_CUSTOMER
                orderByAsc POSTGRESQL_CUSTOMER.id
                limit 1
                ).fetchAll()

    fun selectAllLimitOffset() =
        (sqlClient selectFrom POSTGRESQL_CUSTOMER
                limit 1 offset 1
                ).fetchAll()

    fun selectAllOrderByIdLimitOffset() =
        (sqlClient selectFrom POSTGRESQL_CUSTOMER
                orderByAsc POSTGRESQL_CUSTOMER.id
                limit 2 offset 1
                ).fetchAll()
}
