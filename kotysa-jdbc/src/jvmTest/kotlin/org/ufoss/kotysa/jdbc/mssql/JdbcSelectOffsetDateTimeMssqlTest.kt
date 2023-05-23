/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mssql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MssqlOffsetDateTimes
import org.ufoss.kotysa.test.repositories.blocking.SelectOffsetDateTimeRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectOffsetDateTimeTest

class JdbcSelectOffsetDateTimeMssqlTest : AbstractJdbcMssqlTest<OffsetDateTimeRepositoryMssqlSelect>(),
    SelectOffsetDateTimeTest<MssqlOffsetDateTimes, OffsetDateTimeRepositoryMssqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) =
        OffsetDateTimeRepositoryMssqlSelect(sqlClient)
}

class OffsetDateTimeRepositoryMssqlSelect(sqlClient: JdbcSqlClient) :
    SelectOffsetDateTimeRepository<MssqlOffsetDateTimes>(sqlClient, MssqlOffsetDateTimes)
