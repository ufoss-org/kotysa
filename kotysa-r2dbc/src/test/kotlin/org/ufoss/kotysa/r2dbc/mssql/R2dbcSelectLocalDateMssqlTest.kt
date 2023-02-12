/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mssql

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MssqlLocalDates
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalDateRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalDateTest


class R2dbcSelectLocalDateMssqlTest : AbstractR2dbcMssqlTest<LocalDateRepositoryMssqlSelect>(),
    CoroutinesSelectLocalDateTest<MssqlLocalDates, LocalDateRepositoryMssqlSelect, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = LocalDateRepositoryMssqlSelect(sqlClient)
}

class LocalDateRepositoryMssqlSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectLocalDateRepository<MssqlLocalDates>(sqlClient, MssqlLocalDates)
