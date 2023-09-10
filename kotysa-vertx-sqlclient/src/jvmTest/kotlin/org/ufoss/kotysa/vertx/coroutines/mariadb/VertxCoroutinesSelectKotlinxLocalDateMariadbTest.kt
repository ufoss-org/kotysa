/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mariadb

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MariadbKotlinxLocalDates
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectKotlinxLocalDateRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectKotlinxLocalDateTest


class VertxCoroutinesSelectKotlinxLocalDateMariadbTest : AbstractVertxCoroutinesMariadbTest<KotlinxLocalDateRepositoryMariadbSelect>(),
    CoroutinesSelectKotlinxLocalDateTest<MariadbKotlinxLocalDates, KotlinxLocalDateRepositoryMariadbSelect,
            Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = KotlinxLocalDateRepositoryMariadbSelect(sqlClient)
}

class KotlinxLocalDateRepositoryMariadbSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectKotlinxLocalDateRepository<MariadbKotlinxLocalDates>(sqlClient, MariadbKotlinxLocalDates)
