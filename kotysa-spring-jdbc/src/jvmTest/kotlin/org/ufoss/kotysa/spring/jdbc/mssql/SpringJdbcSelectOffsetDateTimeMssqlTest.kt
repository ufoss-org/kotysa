/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mssql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MssqlOffsetDateTimes
import org.ufoss.kotysa.test.mssqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectOffsetDateTimeRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectOffsetDateTimeTest

class SpringJdbcSelectOffsetDateTimeMssqlTest :
    AbstractSpringJdbcMssqlTest<OffsetDateTimeRepositoryMssqlSelect>(),
    SelectOffsetDateTimeTest<MssqlOffsetDateTimes, OffsetDateTimeRepositoryMssqlSelect,
            SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        OffsetDateTimeRepositoryMssqlSelect(jdbcOperations)
}

class OffsetDateTimeRepositoryMssqlSelect(client: JdbcOperations) :
    SelectOffsetDateTimeRepository<MssqlOffsetDateTimes>(
        client.sqlClient(mssqlTables),
        MssqlOffsetDateTimes
    )
