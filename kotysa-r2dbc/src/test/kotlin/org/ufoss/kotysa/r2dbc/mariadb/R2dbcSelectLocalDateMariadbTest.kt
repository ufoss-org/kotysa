/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mariadb

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MariadbLocalDates
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalDateRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalDateTest


class R2dbcSelectLocalDateMariadbTest : AbstractR2dbcMariadbTest<LocalDateRepositoryMariadbSelect>(),
    CoroutinesSelectLocalDateTest<MariadbLocalDates, LocalDateRepositoryMariadbSelect, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = LocalDateRepositoryMariadbSelect(sqlClient)
}

class LocalDateRepositoryMariadbSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectLocalDateRepository<MariadbLocalDates>(sqlClient, MariadbLocalDates)
