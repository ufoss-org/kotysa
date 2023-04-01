/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MysqlLongTexts
import org.ufoss.kotysa.test.mysqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectStringAsLongTextRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectStringAsLongTextTest

class SpringJdbcSelectStringAsLongTextMysqlTest : AbstractSpringJdbcMysqlTest<StringAsLongTextRepositoryMysqlSelect>(),
    SelectStringAsLongTextTest<MysqlLongTexts, StringAsLongTextRepositoryMysqlSelect, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        StringAsLongTextRepositoryMysqlSelect(jdbcOperations)
}

class StringAsLongTextRepositoryMysqlSelect(client: JdbcOperations) :
    SelectStringAsLongTextRepository<MysqlLongTexts>(client.sqlClient(mysqlTables), MysqlLongTexts)
