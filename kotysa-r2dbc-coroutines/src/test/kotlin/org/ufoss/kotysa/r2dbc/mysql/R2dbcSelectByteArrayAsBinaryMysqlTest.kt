/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MysqlByteArrayAsBinaries
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectByteArrayAsBinaryRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectByteArrayAsBinaryTest

class R2dbcSelectByteArrayAsBinaryMysqlTest : AbstractR2dbcMysqlTest<ByteArrayAsBinaryRepositoryMysqlSelect>(),
    CoroutinesSelectByteArrayAsBinaryTest<MysqlByteArrayAsBinaries, ByteArrayAsBinaryRepositoryMysqlSelect,
            R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = ByteArrayAsBinaryRepositoryMysqlSelect(sqlClient)
}

class ByteArrayAsBinaryRepositoryMysqlSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectByteArrayAsBinaryRepository<MysqlByteArrayAsBinaries>(sqlClient, MysqlByteArrayAsBinaries)
