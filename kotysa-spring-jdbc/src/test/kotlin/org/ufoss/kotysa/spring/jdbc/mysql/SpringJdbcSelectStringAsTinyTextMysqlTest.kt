/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MysqlTinyTexts
import org.ufoss.kotysa.test.mysqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectStringAsTinyTextRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectStringAsTinyTextTest

class SpringJdbcSelectStringAsTinyTextMysqlTest : AbstractSpringJdbcMysqlTest<StringAsTinyTextRepositoryMysqlSelect>(),
    SelectStringAsTinyTextTest<MysqlTinyTexts, StringAsTinyTextRepositoryMysqlSelect, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        StringAsTinyTextRepositoryMysqlSelect(jdbcOperations)
}

class StringAsTinyTextRepositoryMysqlSelect(client: JdbcOperations) :
    SelectStringAsTinyTextRepository<MysqlTinyTexts>(client.sqlClient(mysqlTables), MysqlTinyTexts)
