/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mysql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MysqlByteArrays
import org.ufoss.kotysa.test.repositories.blocking.SelectByteArrayRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectByteArrayTest

class JdbcSelectByteArrayAsBlobMysqlTest : AbstractJdbcMysqlTest<ByteArrayRepositoryMysqlSelect>(),
    SelectByteArrayTest<MysqlByteArrays, ByteArrayRepositoryMysqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = ByteArrayRepositoryMysqlSelect(sqlClient)
}

class ByteArrayRepositoryMysqlSelect(sqlClient: JdbcSqlClient) :
    SelectByteArrayRepository<MysqlByteArrays>(sqlClient, MysqlByteArrays)
