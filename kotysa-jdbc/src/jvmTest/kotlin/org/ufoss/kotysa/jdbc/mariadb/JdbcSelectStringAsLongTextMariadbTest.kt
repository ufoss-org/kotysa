/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mariadb

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MariadbLongTexts
import org.ufoss.kotysa.test.repositories.blocking.SelectStringAsLongTextRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectStringAsLongTextTest

class JdbcSelectStringAsLongTextMariadbTest : AbstractJdbcMariadbTest<StringAsLongTextRepositoryMariadbSelect>(),
    SelectStringAsLongTextTest<MariadbLongTexts, StringAsLongTextRepositoryMariadbSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = StringAsLongTextRepositoryMariadbSelect(sqlClient)
}

class StringAsLongTextRepositoryMariadbSelect(sqlClient: JdbcSqlClient) :
    SelectStringAsLongTextRepository<MariadbLongTexts>(sqlClient, MariadbLongTexts)
