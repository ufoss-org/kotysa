/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mssql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MssqlKotlinxLocalDates
import org.ufoss.kotysa.test.mssqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateTest

class SpringJdbcSelectKotlinxLocalDateMssqlTest : AbstractSpringJdbcMssqlTest<KotlinxLocalDateRepositoryMssqlSelect>(),
    SelectKotlinxLocalDateTest<MssqlKotlinxLocalDates, KotlinxLocalDateRepositoryMssqlSelect, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        KotlinxLocalDateRepositoryMssqlSelect(jdbcOperations)
}

class KotlinxLocalDateRepositoryMssqlSelect(client: JdbcOperations) :
    SelectKotlinxLocalDateRepository<MssqlKotlinxLocalDates>(client.sqlClient(mssqlTables), MssqlKotlinxLocalDates)
