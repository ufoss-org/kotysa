/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mysql

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MysqlByteArrayAsBinaries
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectByteArrayAsBinaryRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectByteArrayAsBinaryTest

class VertxCoroutinesSelectByteArrayAsBinaryMysqlTest : AbstractVertxCoroutinesMysqlTest<ByteArrayAsBinaryRepositoryMysqlSelect>(),
    CoroutinesSelectByteArrayAsBinaryTest<MysqlByteArrayAsBinaries, ByteArrayAsBinaryRepositoryMysqlSelect,
            Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = ByteArrayAsBinaryRepositoryMysqlSelect(sqlClient)
}

class ByteArrayAsBinaryRepositoryMysqlSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectByteArrayAsBinaryRepository<MysqlByteArrayAsBinaries>(sqlClient, MysqlByteArrayAsBinaries)
