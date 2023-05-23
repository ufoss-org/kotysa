/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mssql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MssqlLocalTimes
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalTimeRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalTimeTest

class JdbcSelectLocalTimeMssqlTest : AbstractJdbcMssqlTest<LocalTimeRepositoryMssqlSelect>(),
    SelectLocalTimeTest<MssqlLocalTimes, LocalTimeRepositoryMssqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = LocalTimeRepositoryMssqlSelect(sqlClient)
}

class LocalTimeRepositoryMssqlSelect(sqlClient: JdbcSqlClient) :
    SelectLocalTimeRepository<MssqlLocalTimes>(sqlClient, MssqlLocalTimes)
