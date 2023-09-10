/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mssql

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MssqlCustomers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLimitOffsetRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLimitOffsetTest

class VertxCoroutinesSelectLimitOffsetMssqlTest : AbstractVertxCoroutinesMssqlTest<LimitOffsetRepositoryMssqlSelect>(),
    CoroutinesSelectLimitOffsetTest<MssqlCustomers, LimitOffsetRepositoryMssqlSelect, Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = LimitOffsetRepositoryMssqlSelect(sqlClient)

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

class LimitOffsetRepositoryMssqlSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectLimitOffsetRepository<MssqlCustomers>(sqlClient, MssqlCustomers)
