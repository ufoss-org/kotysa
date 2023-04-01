/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mssql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MssqlKotlinxLocalDateTimes
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateTimeRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateTimeTest

class JdbcSelectKotlinxLocalDateTimeMssqlTest : AbstractJdbcMssqlTest<KotlinxLocalDateTimeRepositoryMssqlSelect>(),
    SelectKotlinxLocalDateTimeTest<MssqlKotlinxLocalDateTimes, KotlinxLocalDateTimeRepositoryMssqlSelect,
            JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = KotlinxLocalDateTimeRepositoryMssqlSelect(sqlClient)
}

class KotlinxLocalDateTimeRepositoryMssqlSelect(sqlClient: JdbcSqlClient) :
    SelectKotlinxLocalDateTimeRepository<MssqlKotlinxLocalDateTimes>(sqlClient, MssqlKotlinxLocalDateTimes)
