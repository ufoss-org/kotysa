/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mariadb

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MariadbKotlinxLocalDates
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectKotlinxLocalDateRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectKotlinxLocalDateTest


class R2dbcSelectKotlinxLocalDateMariadbTest : AbstractR2dbcMariadbTest<KotlinxLocalDateRepositoryMariadbSelect>(),
    CoroutinesSelectKotlinxLocalDateTest<MariadbKotlinxLocalDates, KotlinxLocalDateRepositoryMariadbSelect,
            R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = KotlinxLocalDateRepositoryMariadbSelect(sqlClient)
}

class KotlinxLocalDateRepositoryMariadbSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectKotlinxLocalDateRepository<MariadbKotlinxLocalDates>(sqlClient, MariadbKotlinxLocalDates)
