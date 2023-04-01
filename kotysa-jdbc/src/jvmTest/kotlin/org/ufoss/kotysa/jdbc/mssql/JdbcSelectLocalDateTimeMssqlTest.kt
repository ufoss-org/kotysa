/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mssql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MssqlLocalDateTimes
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateTimeRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateTimeTest

class JdbcSelectLocalDateTimeMssqlTest : AbstractJdbcMssqlTest<LocalDateTimeRepositoryMssqlSelect>(),
    SelectLocalDateTimeTest<MssqlLocalDateTimes, LocalDateTimeRepositoryMssqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = LocalDateTimeRepositoryMssqlSelect(sqlClient)
}

class LocalDateTimeRepositoryMssqlSelect(sqlClient: JdbcSqlClient) :
    SelectLocalDateTimeRepository<MssqlLocalDateTimes>(sqlClient, MssqlLocalDateTimes)
