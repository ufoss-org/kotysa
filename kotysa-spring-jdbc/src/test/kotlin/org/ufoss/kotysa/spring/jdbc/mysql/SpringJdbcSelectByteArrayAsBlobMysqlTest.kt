/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MysqlByteArrays
import org.ufoss.kotysa.test.mysqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectByteArrayRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectByteArrayTest

class SpringJdbcSelectByteArrayAsBlobMysqlTest : AbstractSpringJdbcMysqlTest<ByteArrayRepositoryMysqlSelect>(),
    SelectByteArrayTest<MysqlByteArrays, ByteArrayRepositoryMysqlSelect, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) = ByteArrayRepositoryMysqlSelect(jdbcOperations)
}

class ByteArrayRepositoryMysqlSelect(client: JdbcOperations) :
    SelectByteArrayRepository<MysqlByteArrays>(client.sqlClient(mysqlTables), MysqlByteArrays)
