/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Order
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.repositories.blocking.InsertRepository
import org.ufoss.kotysa.test.repositories.blocking.InsertTest

@Order(3)
class SpringJdbcInsertMysqlTest : AbstractSpringJdbcMysqlTest<RepositoryMysqlInsert>(),
    InsertTest<MysqlInts, MysqlLongs, MysqlCustomers, RepositoryMysqlInsert, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<RepositoryMysqlInsert>(resource)
    }

    override val repository: RepositoryMysqlInsert by lazy {
        getContextRepository()
    }

    override val exceptionClass = DataIntegrityViolationException::class.java
}

class RepositoryMysqlInsert(dbClient: JdbcOperations) :
    InsertRepository<MysqlInts, MysqlLongs, MysqlCustomers>(
        dbClient.sqlClient(mysqlTables),
        MysqlInts,
        MysqlLongs,
        MysqlCustomers
    )
