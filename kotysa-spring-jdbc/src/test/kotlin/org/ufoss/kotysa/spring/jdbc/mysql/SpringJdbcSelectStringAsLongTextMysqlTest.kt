/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MysqlLongTexts
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mysqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectStringAsLongTextRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectStringAsLongTextTest

class SpringJdbcSelectStringAsLongTextMysqlTest : AbstractSpringJdbcMysqlTest<StringAsLongTextRepositoryMysqlSelect>(),
    SelectStringAsLongTextTest<MysqlLongTexts, StringAsLongTextRepositoryMysqlSelect, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<StringAsLongTextRepositoryMysqlSelect>(resource)
    }

    override val repository: StringAsLongTextRepositoryMysqlSelect by lazy {
        getContextRepository()
    }
}

class StringAsLongTextRepositoryMysqlSelect(client: JdbcOperations) :
    SelectStringAsLongTextRepository<MysqlLongTexts>(client.sqlClient(mysqlTables), MysqlLongTexts)
