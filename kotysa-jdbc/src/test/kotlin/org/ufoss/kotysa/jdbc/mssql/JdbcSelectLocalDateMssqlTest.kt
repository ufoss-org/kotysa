/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mssql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MssqlLocalDates
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateTest

class JdbcSelectLocalDateMssqlTest : AbstractJdbcMssqlTest<LocalDateRepositoryMssqlSelect>(),
    SelectLocalDateTest<MssqlLocalDates, LocalDateRepositoryMssqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = LocalDateRepositoryMssqlSelect(sqlClient)
}

class LocalDateRepositoryMssqlSelect(sqlClient: JdbcSqlClient) :
    SelectLocalDateRepository<MssqlLocalDates>(sqlClient, MssqlLocalDates)
