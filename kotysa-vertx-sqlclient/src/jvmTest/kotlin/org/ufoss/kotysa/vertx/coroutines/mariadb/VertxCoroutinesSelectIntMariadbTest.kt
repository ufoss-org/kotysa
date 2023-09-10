/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mariadb

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectIntRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectIntTest

@Order(1)
class VertxCoroutinesSelectIntMariadbTest : AbstractVertxCoroutinesMariadbTest<SelectIntRepositoryMariadbSelect>(),
    CoroutinesSelectIntTest<MariadbInts, SelectIntRepositoryMariadbSelect, Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = SelectIntRepositoryMariadbSelect(sqlClient)
}

class SelectIntRepositoryMariadbSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectIntRepository<MariadbInts>(sqlClient, MariadbInts)
