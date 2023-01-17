/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mariadb

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MariadbKotlinxLocalTimes
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectKotlinxLocalTimeRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectKotlinxLocalTimeTest

class R2dbcSelectKotlinxLocalTimeMariadbTest : AbstractR2dbcMariadbTest<KotlinxLocalTimeRepositoryMariadbSelect>(),
    CoroutinesSelectKotlinxLocalTimeTest<MariadbKotlinxLocalTimes, KotlinxLocalTimeRepositoryMariadbSelect, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = KotlinxLocalTimeRepositoryMariadbSelect(sqlClient)
}

class KotlinxLocalTimeRepositoryMariadbSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectKotlinxLocalTimeRepository<MariadbKotlinxLocalTimes>(sqlClient, MariadbKotlinxLocalTimes)
