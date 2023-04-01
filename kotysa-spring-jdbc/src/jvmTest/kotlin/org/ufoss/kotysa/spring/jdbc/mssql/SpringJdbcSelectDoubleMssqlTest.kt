/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mssql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MssqlDoubles
import org.ufoss.kotysa.test.mssqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectDoubleRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectDoubleTest

class SpringJdbcSelectDoubleMssqlTest : AbstractSpringJdbcMssqlTest<DoubleRepositoryMssqlSelect>(),
    SelectDoubleTest<MssqlDoubles, DoubleRepositoryMssqlSelect, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) = DoubleRepositoryMssqlSelect(jdbcOperations)
}

class DoubleRepositoryMssqlSelect(client: JdbcOperations) :
    SelectDoubleRepository<MssqlDoubles>(client.sqlClient(mssqlTables), MssqlDoubles)
