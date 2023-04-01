/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mssql

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Ignore
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MssqlCustomers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLimitOffsetRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLimitOffsetTest

class R2dbcSelectLimitOffsetMssqlTest : AbstractR2dbcMssqlTest<LimitOffsetRepositoryMssqlSelect>(),
    CoroutinesSelectLimitOffsetTest<MssqlCustomers, LimitOffsetRepositoryMssqlSelect, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = LimitOffsetRepositoryMssqlSelect(sqlClient)

    @Test
    fun `Verify selectAllLimitOffset throw exception`() {
        assertThatThrownBy {
            runTest {
                repository.selectAllLimitOffset().collect()
            }
        }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Mssql offset or limit must have order by")
    }
    
    override fun `Verify selectAllLimitOffset returns one result`() {}
}

class LimitOffsetRepositoryMssqlSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectLimitOffsetRepository<MssqlCustomers>(sqlClient, MssqlCustomers)
