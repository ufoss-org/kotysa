/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mariadb

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MariadbKotlinxLocalDateTimes
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectKotlinxLocalDateTimeRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectKotlinxLocalDateTimeTest


class R2dbcSelectKotlinxLocalDateTimeMariadbTest : AbstractR2dbcMariadbTest<KotlinxLocalDateTimeRepositoryMariadbSelect>(),
    CoroutinesSelectKotlinxLocalDateTimeTest<MariadbKotlinxLocalDateTimes, KotlinxLocalDateTimeRepositoryMariadbSelect,
            R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = KotlinxLocalDateTimeRepositoryMariadbSelect(sqlClient)
}

class KotlinxLocalDateTimeRepositoryMariadbSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectKotlinxLocalDateTimeRepository<MariadbKotlinxLocalDateTimes>(sqlClient, MariadbKotlinxLocalDateTimes)
