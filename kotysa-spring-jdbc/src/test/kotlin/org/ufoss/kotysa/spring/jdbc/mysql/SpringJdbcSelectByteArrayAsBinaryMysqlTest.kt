/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MysqlByteArrayAsBinaries
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mysqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectByteArrayAsBinaryRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectByteArrayAsBinaryTest

class SpringJdbcSelectByteArrayAsBinaryMysqlTest :
    AbstractSpringJdbcMysqlTest<ByteArrayAsBinaryRepositoryMysqlSelect>(),
    SelectByteArrayAsBinaryTest<MysqlByteArrayAsBinaries, ByteArrayAsBinaryRepositoryMysqlSelect,
            SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<ByteArrayAsBinaryRepositoryMysqlSelect>(resource)
    }

    override val repository: ByteArrayAsBinaryRepositoryMysqlSelect by lazy {
        getContextRepository()
    }
}

class ByteArrayAsBinaryRepositoryMysqlSelect(client: JdbcOperations) :
    SelectByteArrayAsBinaryRepository<MysqlByteArrayAsBinaries>(client.sqlClient(mysqlTables), MysqlByteArrayAsBinaries)
