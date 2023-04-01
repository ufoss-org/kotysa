/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mssql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MssqlKotlinxLocalDateTimes
import org.ufoss.kotysa.test.mssqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateTimeRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateTimeTest

class SpringJdbcSelectKotlinxLocalDateTimeMssqlTest :
    AbstractSpringJdbcMssqlTest<KotlinxLocalDateTimeRepositoryMssqlSelect>(),
    SelectKotlinxLocalDateTimeTest<MssqlKotlinxLocalDateTimes, KotlinxLocalDateTimeRepositoryMssqlSelect,
            SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        KotlinxLocalDateTimeRepositoryMssqlSelect(jdbcOperations)
}

class KotlinxLocalDateTimeRepositoryMssqlSelect(client: JdbcOperations) :
    SelectKotlinxLocalDateTimeRepository<MssqlKotlinxLocalDateTimes>(
        client.sqlClient(mssqlTables),
        MssqlKotlinxLocalDateTimes
    )
