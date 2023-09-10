/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mariadb

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MariadbByteArrayAsBinaries
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectByteArrayAsBinaryRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectByteArrayAsBinaryTest

class VertxCoroutinesSelectByteArrayAsBinaryMariadbTest : AbstractVertxCoroutinesMariadbTest<ByteArrayAsBinaryRepositoryMariadbSelect>(),
    CoroutinesSelectByteArrayAsBinaryTest<MariadbByteArrayAsBinaries, ByteArrayAsBinaryRepositoryMariadbSelect,
            Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = ByteArrayAsBinaryRepositoryMariadbSelect(sqlClient)
}

class ByteArrayAsBinaryRepositoryMariadbSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectByteArrayAsBinaryRepository<MariadbByteArrayAsBinaries>(sqlClient, MariadbByteArrayAsBinaries)
