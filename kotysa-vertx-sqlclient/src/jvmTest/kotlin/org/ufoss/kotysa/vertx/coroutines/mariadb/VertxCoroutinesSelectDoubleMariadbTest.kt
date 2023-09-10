/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mariadb

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MariadbDoubles
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectDoubleRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectDoubleTest

class VertxCoroutinesSelectDoubleMariadbTest : AbstractVertxCoroutinesMariadbTest<DoubleRepositoryMariadbSelect>(),
    CoroutinesSelectDoubleTest<MariadbDoubles, DoubleRepositoryMariadbSelect,
            Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = DoubleRepositoryMariadbSelect(sqlClient)
}

class DoubleRepositoryMariadbSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectDoubleRepository<MariadbDoubles>(sqlClient, MariadbDoubles)
