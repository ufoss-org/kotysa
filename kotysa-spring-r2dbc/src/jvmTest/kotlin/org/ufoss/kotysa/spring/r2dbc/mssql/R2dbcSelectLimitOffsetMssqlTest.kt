/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mssql

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.MssqlCoroutinesSqlClient
import org.ufoss.kotysa.MssqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.test.MssqlCustomers
import org.ufoss.kotysa.test.customerFrance
import org.ufoss.kotysa.test.customerUSA1
import org.ufoss.kotysa.test.customerUSA2

class R2dbcSelectLimitOffsetMssqlTest : AbstractR2dbcMssqlTest<LimitOffsetRepositoryMssqlSelect>() {

    override fun instantiateRepository(sqlClient: MssqlReactorSqlClient, coSqlClient: MssqlCoroutinesSqlClient) =
        LimitOffsetRepositoryMssqlSelect(sqlClient)

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
    fun `Verify selectAllLimitOffset throw exception`() {
        assertThatThrownBy { repository.selectAllLimitOffset().blockLast() }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Mssql offset or limit must have order by")
    }

    @Test
    fun `Verify selectAllOrderByIdLimitOffset returns customerUSA1`() {
        assertThat(repository.selectAllOrderByIdLimitOffset().toIterable())
            .hasSize(2)
            .containsExactly(customerUSA1, customerUSA2)
    }
}

class LimitOffsetRepositoryMssqlSelect(sqlClient: ReactorSqlClient) : AbstractCustomerRepositoryMssql(sqlClient) {

    fun selectAllOrderByIdOffset() =
        (sqlClient selectFrom MssqlCustomers
                orderByAsc MssqlCustomers.id
                offset 2
                ).fetchAll()

    fun selectAllOrderByIdLimit() =
        (sqlClient selectFrom MssqlCustomers
                orderByAsc MssqlCustomers.id
                limit 1
                ).fetchAll()

    fun selectAllLimitOffset() =
        (sqlClient selectFrom MssqlCustomers
                limit 1 offset 1
                ).fetchAll()

    fun selectAllOrderByIdLimitOffset() =
        (sqlClient selectFrom MssqlCustomers
                orderByAsc MssqlCustomers.id
                limit 2 offset 1
                ).fetchAll()
}
