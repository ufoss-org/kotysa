/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mssql

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MssqlCustomers
import org.ufoss.kotysa.test.repositories.blocking.SelectLimitOffsetRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLimitOffsetTest

class JdbcSelectLimitOffsetMssqlTest : AbstractJdbcMssqlTest<LimitOffsetByRepositoryMssqlSelect>(),
    SelectLimitOffsetTest<MssqlCustomers, LimitOffsetByRepositoryMssqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = LimitOffsetByRepositoryMssqlSelect(sqlClient)

    @Test
    override fun `Verify selectAllLimitOffset returns one result`() {
        assertThatThrownBy { super.`Verify selectAllLimitOffset returns one result`() }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Mssql offset or limit must have order by")
    }
}

class LimitOffsetByRepositoryMssqlSelect(sqlClient: JdbcSqlClient) :
    SelectLimitOffsetRepository<MssqlCustomers>(sqlClient, MssqlCustomers)
