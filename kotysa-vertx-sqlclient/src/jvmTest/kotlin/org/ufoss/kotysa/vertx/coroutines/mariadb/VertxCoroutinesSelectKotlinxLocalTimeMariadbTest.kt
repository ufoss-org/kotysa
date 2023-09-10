/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mariadb

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MariadbKotlinxLocalTimes
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectKotlinxLocalTimeRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectKotlinxLocalTimeTest

class VertxCoroutinesSelectKotlinxLocalTimeMariadbTest : AbstractVertxCoroutinesMariadbTest<KotlinxLocalTimeRepositoryMariadbSelect>(),
    CoroutinesSelectKotlinxLocalTimeTest<MariadbKotlinxLocalTimes, KotlinxLocalTimeRepositoryMariadbSelect, Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = KotlinxLocalTimeRepositoryMariadbSelect(sqlClient)
}

class KotlinxLocalTimeRepositoryMariadbSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectKotlinxLocalTimeRepository<MariadbKotlinxLocalTimes>(sqlClient, MariadbKotlinxLocalTimes)
