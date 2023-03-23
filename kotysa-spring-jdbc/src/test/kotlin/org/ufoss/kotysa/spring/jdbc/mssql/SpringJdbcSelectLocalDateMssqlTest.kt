/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mssql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MssqlLocalDates
import org.ufoss.kotysa.test.mssqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateTest

class SpringJdbcSelectLocalDateMssqlTest : AbstractSpringJdbcMssqlTest<LocalDateRepositoryMssqlSelect>(),
    SelectLocalDateTest<MssqlLocalDates, LocalDateRepositoryMssqlSelect, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) = LocalDateRepositoryMssqlSelect(jdbcOperations)
}

class LocalDateRepositoryMssqlSelect(client: JdbcOperations) :
    SelectLocalDateRepository<MssqlLocalDates>(client.sqlClient(mssqlTables), MssqlLocalDates)
