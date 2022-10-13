/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mysql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MysqlByteArrayAsBinaries
import org.ufoss.kotysa.test.repositories.blocking.SelectByteArrayAsBinaryRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectByteArrayAsBinaryTest

class JdbcSelectByteArrayAsBinaryMysqlTest : AbstractJdbcMysqlTest<ByteArrayAsBinaryRepositoryMysqlSelect>(),
    SelectByteArrayAsBinaryTest<MysqlByteArrayAsBinaries, ByteArrayAsBinaryRepositoryMysqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = ByteArrayAsBinaryRepositoryMysqlSelect(sqlClient)
}

class ByteArrayAsBinaryRepositoryMysqlSelect(sqlClient: JdbcSqlClient) :
    SelectByteArrayAsBinaryRepository<MysqlByteArrayAsBinaries>(sqlClient, MysqlByteArrayAsBinaries)
