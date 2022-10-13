/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mssql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MssqlByteArrayAsBinaries
import org.ufoss.kotysa.test.repositories.blocking.SelectByteArrayAsBinaryRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectByteArrayAsBinaryTest

class JdbcSelectByteArrayAsBinaryMssqlTest : AbstractJdbcMssqlTest<ByteArrayAsBinaryRepositoryMssqlSelect>(),
    SelectByteArrayAsBinaryTest<MssqlByteArrayAsBinaries, ByteArrayAsBinaryRepositoryMssqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = ByteArrayAsBinaryRepositoryMssqlSelect(sqlClient)
}

class ByteArrayAsBinaryRepositoryMssqlSelect(sqlClient: JdbcSqlClient) :
    SelectByteArrayAsBinaryRepository<MssqlByteArrayAsBinaries>(sqlClient, MssqlByteArrayAsBinaries)
