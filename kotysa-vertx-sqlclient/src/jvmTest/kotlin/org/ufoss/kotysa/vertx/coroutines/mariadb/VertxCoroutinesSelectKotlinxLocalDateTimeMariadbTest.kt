/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mariadb

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MariadbKotlinxLocalDateTimes
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectKotlinxLocalDateTimeRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectKotlinxLocalDateTimeTest


class VertxCoroutinesSelectKotlinxLocalDateTimeMariadbTest : AbstractVertxCoroutinesMariadbTest<KotlinxLocalDateTimeRepositoryMariadbSelect>(),
    CoroutinesSelectKotlinxLocalDateTimeTest<MariadbKotlinxLocalDateTimes, KotlinxLocalDateTimeRepositoryMariadbSelect,
            Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = KotlinxLocalDateTimeRepositoryMariadbSelect(sqlClient)
}

class KotlinxLocalDateTimeRepositoryMariadbSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectKotlinxLocalDateTimeRepository<MariadbKotlinxLocalDateTimes>(sqlClient, MariadbKotlinxLocalDateTimes)
