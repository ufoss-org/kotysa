/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mssql

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MssqlByteArrayAsBinaries
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectByteArrayAsBinaryRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectByteArrayAsBinaryTest

class R2dbcSelectByteArrayAsBinaryMssqlTest : AbstractR2dbcMssqlTest<ByteArrayAsBinaryRepositoryMssqlSelect>(),
    CoroutinesSelectByteArrayAsBinaryTest<MssqlByteArrayAsBinaries, ByteArrayAsBinaryRepositoryMssqlSelect,
            R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = ByteArrayAsBinaryRepositoryMssqlSelect(sqlClient)
}

class ByteArrayAsBinaryRepositoryMssqlSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectByteArrayAsBinaryRepository<MssqlByteArrayAsBinaries>(sqlClient, MssqlByteArrayAsBinaries)
