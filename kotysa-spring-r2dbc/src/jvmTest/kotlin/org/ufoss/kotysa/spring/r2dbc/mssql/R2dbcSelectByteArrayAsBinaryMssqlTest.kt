/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mssql

import org.ufoss.kotysa.MssqlCoroutinesSqlClient
import org.ufoss.kotysa.MssqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MssqlByteArrayAsBinaries
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectByteArrayAsBinaryRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectByteArrayAsBinaryTest

class R2dbcSelectByteArrayAsBinaryMssqlTest : AbstractR2dbcMssqlTest<ByteArrayRepositoryMssqlSelect>(),
    ReactorSelectByteArrayAsBinaryTest<MssqlByteArrayAsBinaries, ByteArrayRepositoryMssqlSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MssqlReactorSqlClient, coSqlClient: MssqlCoroutinesSqlClient) =
        ByteArrayRepositoryMssqlSelect(sqlClient)
}

class ByteArrayRepositoryMssqlSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectByteArrayAsBinaryRepository<MssqlByteArrayAsBinaries>(sqlClient, MssqlByteArrayAsBinaries)
