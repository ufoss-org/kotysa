/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mariadb

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MariadbTexts
import org.ufoss.kotysa.test.repositories.blocking.SelectStringAsTextRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectStringAsTextTest

class JdbcSelectStringAsTextMariadbTest : AbstractJdbcMariadbTest<StringAsTextRepositoryMariadbSelect>(),
    SelectStringAsTextTest<MariadbTexts, StringAsTextRepositoryMariadbSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = StringAsTextRepositoryMariadbSelect(sqlClient)
}

class StringAsTextRepositoryMariadbSelect(sqlClient: JdbcSqlClient) :
    SelectStringAsTextRepository<MariadbTexts>(sqlClient, MariadbTexts)
