/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mysql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MysqlLongTexts
import org.ufoss.kotysa.test.repositories.blocking.SelectStringAsLongTextRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectStringAsLongTextTest

class JdbcSelectStringAsLongTextMysqlTest : AbstractJdbcMysqlTest<StringAsLongTextRepositoryMysqlSelect>(),
    SelectStringAsLongTextTest<MysqlLongTexts, StringAsLongTextRepositoryMysqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = StringAsLongTextRepositoryMysqlSelect(sqlClient)
}

class StringAsLongTextRepositoryMysqlSelect(sqlClient: JdbcSqlClient) :
    SelectStringAsLongTextRepository<MysqlLongTexts>(sqlClient, MysqlLongTexts)
