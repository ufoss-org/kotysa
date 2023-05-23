/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mssql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MssqlKotlinxLocalTimes
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalTimeRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalTimeTest

class JdbcSelectKotlinxLocalTimeMssqlTest :
    AbstractJdbcMssqlTest<KotlinxLocalTimeRepositoryMssqlSelect>(),
    SelectKotlinxLocalTimeTest<MssqlKotlinxLocalTimes, KotlinxLocalTimeRepositoryMssqlSelect,
            JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) =
        KotlinxLocalTimeRepositoryMssqlSelect(sqlClient)
}

class KotlinxLocalTimeRepositoryMssqlSelect(sqlClient: JdbcSqlClient) :
    SelectKotlinxLocalTimeRepository<MssqlKotlinxLocalTimes>(sqlClient, MssqlKotlinxLocalTimes)
