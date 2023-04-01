/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.H2LocalDates
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalDateRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalDateTest


class R2dbcSelectLocalDateH2Test : AbstractR2dbcH2Test<LocalDateRepositoryH2Select>(),
    CoroutinesSelectLocalDateTest<H2LocalDates, LocalDateRepositoryH2Select, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = LocalDateRepositoryH2Select(sqlClient)
}

class LocalDateRepositoryH2Select(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectLocalDateRepository<H2LocalDates>(sqlClient, H2LocalDates)
