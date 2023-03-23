/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MysqlTexts
import org.ufoss.kotysa.test.mysqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectStringAsTextRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectStringAsTextTest

class SpringJdbcSelectStringAsTextMysqlTest : AbstractSpringJdbcMysqlTest<StringAsTextRepositoryMysqlSelect>(),
    SelectStringAsTextTest<MysqlTexts, StringAsTextRepositoryMysqlSelect, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        StringAsTextRepositoryMysqlSelect(jdbcOperations)
}

class StringAsTextRepositoryMysqlSelect(client: JdbcOperations) :
    SelectStringAsTextRepository<MysqlTexts>(client.sqlClient(mysqlTables), MysqlTexts)
