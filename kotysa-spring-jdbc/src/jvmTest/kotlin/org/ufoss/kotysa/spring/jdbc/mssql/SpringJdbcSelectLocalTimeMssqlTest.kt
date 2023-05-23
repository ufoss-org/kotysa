/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mssql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MssqlLocalTimes
import org.ufoss.kotysa.test.mssqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalTimeRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalTimeTest

class SpringJdbcSelectLocalTimeMssqlTest : AbstractSpringJdbcMssqlTest<LocalTimeRepositoryMssqlSelect>(),
    SelectLocalTimeTest<MssqlLocalTimes, LocalTimeRepositoryMssqlSelect, SpringJdbcTransaction> {
    override fun instantiateRepository(jdbcOperations: JdbcOperations) = LocalTimeRepositoryMssqlSelect(jdbcOperations)
}

class LocalTimeRepositoryMssqlSelect(client: JdbcOperations) :
    SelectLocalTimeRepository<MssqlLocalTimes>(client.sqlClient(mssqlTables), MssqlLocalTimes)
