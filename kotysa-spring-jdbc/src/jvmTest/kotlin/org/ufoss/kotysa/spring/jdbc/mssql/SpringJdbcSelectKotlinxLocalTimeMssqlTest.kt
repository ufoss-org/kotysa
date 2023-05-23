/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mssql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MssqlKotlinxLocalTimes
import org.ufoss.kotysa.test.mssqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalTimeRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalTimeTest

class SpringJdbcSelectKotlinxLocalTimeMssqlTest : AbstractSpringJdbcMssqlTest<KotlinxLocalTimeRepositoryMssqlSelect>(),
    SelectKotlinxLocalTimeTest<MssqlKotlinxLocalTimes, KotlinxLocalTimeRepositoryMssqlSelect, SpringJdbcTransaction> {
    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        KotlinxLocalTimeRepositoryMssqlSelect(jdbcOperations)
}

class KotlinxLocalTimeRepositoryMssqlSelect(client: JdbcOperations) :
    SelectKotlinxLocalTimeRepository<MssqlKotlinxLocalTimes>(client.sqlClient(mssqlTables), MssqlKotlinxLocalTimes)
