/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mssql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MssqlKotlinxLocalDates
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateTest

class JdbcSelectKotlinxLocalDateMssqlTest : AbstractJdbcMssqlTest<KotlinxLocalDateRepositoryMssqlSelect>(),
    SelectKotlinxLocalDateTest<MssqlKotlinxLocalDates, KotlinxLocalDateRepositoryMssqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = KotlinxLocalDateRepositoryMssqlSelect(sqlClient)
}

class KotlinxLocalDateRepositoryMssqlSelect(sqlClient: JdbcSqlClient) :
    SelectKotlinxLocalDateRepository<MssqlKotlinxLocalDates>(sqlClient, MssqlKotlinxLocalDates)
