/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mysql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MysqlTinyTexts
import org.ufoss.kotysa.test.repositories.blocking.SelectStringAsTinyTextRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectStringAsTinyTextTest

class JdbcSelectStringAsTinyTextMysqlTest : AbstractJdbcMysqlTest<StringAsTinyTextRepositoryMysqlSelect>(),
    SelectStringAsTinyTextTest<MysqlTinyTexts, StringAsTinyTextRepositoryMysqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = StringAsTinyTextRepositoryMysqlSelect(sqlClient)
}

class StringAsTinyTextRepositoryMysqlSelect(sqlClient: JdbcSqlClient) :
    SelectStringAsTinyTextRepository<MysqlTinyTexts>(sqlClient, MysqlTinyTexts)
