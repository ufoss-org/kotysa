/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mssql

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MssqlByteArrayAsBinaries
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectByteArrayAsBinaryRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectByteArrayAsBinaryTest

class VertxCoroutinesSelectByteArrayAsBinaryMssqlTest : AbstractVertxCoroutinesMssqlTest<ByteArrayAsBinaryRepositoryMssqlSelect>(),
    CoroutinesSelectByteArrayAsBinaryTest<MssqlByteArrayAsBinaries, ByteArrayAsBinaryRepositoryMssqlSelect,
            Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = ByteArrayAsBinaryRepositoryMssqlSelect(sqlClient)
}

class ByteArrayAsBinaryRepositoryMssqlSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectByteArrayAsBinaryRepository<MssqlByteArrayAsBinaries>(sqlClient, MssqlByteArrayAsBinaries)
