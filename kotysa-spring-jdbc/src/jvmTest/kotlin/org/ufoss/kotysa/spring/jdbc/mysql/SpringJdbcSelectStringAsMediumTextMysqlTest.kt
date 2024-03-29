/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MysqlMediumTexts
import org.ufoss.kotysa.test.mysqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectStringAsMediumTextRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectStringAsMediumTextTest

class SpringJdbcSelectStringAsMediumTextMysqlTest :
    AbstractSpringJdbcMysqlTest<StringAsMediumTextRepositoryMysqlSelect>(),
    SelectStringAsMediumTextTest<MysqlMediumTexts, StringAsMediumTextRepositoryMysqlSelect, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        StringAsMediumTextRepositoryMysqlSelect(jdbcOperations)
}

class StringAsMediumTextRepositoryMysqlSelect(client: JdbcOperations) :
    SelectStringAsMediumTextRepository<MysqlMediumTexts>(client.sqlClient(mysqlTables), MysqlMediumTexts)
