/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mysql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MysqlMediumTexts
import org.ufoss.kotysa.test.repositories.blocking.SelectStringAsMediumTextRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectStringAsMediumTextTest

class JdbcSelectStringAsMediumTextMysqlTest : AbstractJdbcMysqlTest<StringAsMediumTextRepositoryMysqlSelect>(),
    SelectStringAsMediumTextTest<MysqlMediumTexts, StringAsMediumTextRepositoryMysqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = StringAsMediumTextRepositoryMysqlSelect(sqlClient)
}

class StringAsMediumTextRepositoryMysqlSelect(sqlClient: JdbcSqlClient) :
    SelectStringAsMediumTextRepository<MysqlMediumTexts>(sqlClient, MysqlMediumTexts)
