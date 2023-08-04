/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mssql

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.MssqlCoroutinesSqlClient
import org.ufoss.kotysa.MssqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MssqlCustomers
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLimitOffsetRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLimitOffsetTest

class R2dbcSelectLimitOffsetMssqlTest : AbstractR2dbcMssqlTest<LimitOffsetRepositoryMssqlSelect>(),
    ReactorSelectLimitOffsetTest<MssqlCustomers, LimitOffsetRepositoryMssqlSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MssqlReactorSqlClient, coSqlClient: MssqlCoroutinesSqlClient) =
        LimitOffsetRepositoryMssqlSelect(sqlClient)

    @Test
    fun `Verify selectAllLimitOffset throw exception`() {
        assertThatThrownBy { repository.selectAllLimitOffset().blockLast() }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Mssql offset or limit must have order by")
    }

    override fun `Verify selectAllLimitOffset returns one result`() {}
}

class LimitOffsetRepositoryMssqlSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectLimitOffsetRepository<MssqlCustomers>(sqlClient, MssqlCustomers)
