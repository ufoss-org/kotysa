/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mysql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MysqlTexts
import org.ufoss.kotysa.test.repositories.blocking.SelectStringAsTextRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectStringAsTextTest

class JdbcSelectStringAsTextMysqlTest : AbstractJdbcMysqlTest<StringAsTextRepositoryMysqlSelect>(),
    SelectStringAsTextTest<MysqlTexts, StringAsTextRepositoryMysqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = StringAsTextRepositoryMysqlSelect(sqlClient)
}

class StringAsTextRepositoryMysqlSelect(sqlClient: JdbcSqlClient) :
    SelectStringAsTextRepository<MysqlTexts>(sqlClient, MysqlTexts)
