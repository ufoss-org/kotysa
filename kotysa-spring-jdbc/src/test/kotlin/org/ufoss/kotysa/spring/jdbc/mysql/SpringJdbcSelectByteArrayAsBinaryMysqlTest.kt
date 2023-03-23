/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MysqlByteArrayAsBinaries
import org.ufoss.kotysa.test.mysqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectByteArrayAsBinaryRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectByteArrayAsBinaryTest

class SpringJdbcSelectByteArrayAsBinaryMysqlTest :
    AbstractSpringJdbcMysqlTest<ByteArrayAsBinaryRepositoryMysqlSelect>(),
    SelectByteArrayAsBinaryTest<MysqlByteArrayAsBinaries, ByteArrayAsBinaryRepositoryMysqlSelect,
            SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        ByteArrayAsBinaryRepositoryMysqlSelect(jdbcOperations)
}

class ByteArrayAsBinaryRepositoryMysqlSelect(client: JdbcOperations) :
    SelectByteArrayAsBinaryRepository<MysqlByteArrayAsBinaries>(client.sqlClient(mysqlTables), MysqlByteArrayAsBinaries)
