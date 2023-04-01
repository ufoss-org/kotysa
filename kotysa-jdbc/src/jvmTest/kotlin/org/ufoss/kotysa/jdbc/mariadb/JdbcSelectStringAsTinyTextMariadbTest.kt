/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mariadb

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MariadbTinyTexts
import org.ufoss.kotysa.test.repositories.blocking.SelectStringAsTinyTextRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectStringAsTinyTextTest

class JdbcSelectStringAsTinyTextMariadbTest : AbstractJdbcMariadbTest<StringAsTinyTextRepositoryMariadbSelect>(),
    SelectStringAsTinyTextTest<MariadbTinyTexts, StringAsTinyTextRepositoryMariadbSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = StringAsTinyTextRepositoryMariadbSelect(sqlClient)
}

class StringAsTinyTextRepositoryMariadbSelect(sqlClient: JdbcSqlClient) :
    SelectStringAsTinyTextRepository<MariadbTinyTexts>(sqlClient, MariadbTinyTexts)
