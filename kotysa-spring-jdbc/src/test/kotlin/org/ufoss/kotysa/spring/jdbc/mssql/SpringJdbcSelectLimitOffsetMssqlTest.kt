/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mssql

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MssqlCustomers
import org.ufoss.kotysa.test.mssqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectLimitOffsetRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLimitOffsetTest

class SpringJdbcSelectLimitOffsetMssqlTest : AbstractSpringJdbcMssqlTest<LimitOffsetByRepositoryMssqlSelect>(),
    SelectLimitOffsetTest<MssqlCustomers, LimitOffsetByRepositoryMssqlSelect, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        LimitOffsetByRepositoryMssqlSelect(jdbcOperations)

    @Test
    override fun `Verify selectAllLimitOffset returns one result`() {
        assertThatThrownBy { super.`Verify selectAllLimitOffset returns one result`() }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Mssql offset or limit must have order by")
    }
}

class LimitOffsetByRepositoryMssqlSelect(client: JdbcOperations) :
    SelectLimitOffsetRepository<MssqlCustomers>(client.sqlClient(mssqlTables), MssqlCustomers)
