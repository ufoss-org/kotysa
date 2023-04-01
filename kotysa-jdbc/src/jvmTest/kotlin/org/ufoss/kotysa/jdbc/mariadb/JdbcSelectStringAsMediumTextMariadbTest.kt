/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mariadb

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MariadbMediumTexts
import org.ufoss.kotysa.test.repositories.blocking.SelectStringAsMediumTextRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectStringAsMediumTextTest

class JdbcSelectStringAsMediumTextMariadbTest : AbstractJdbcMariadbTest<StringAsMediumTextRepositoryMariadbSelect>(),
    SelectStringAsMediumTextTest<MariadbMediumTexts, StringAsMediumTextRepositoryMariadbSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = StringAsMediumTextRepositoryMariadbSelect(sqlClient)
}

class StringAsMediumTextRepositoryMariadbSelect(sqlClient: JdbcSqlClient) :
    SelectStringAsMediumTextRepository<MariadbMediumTexts>(sqlClient, MariadbMediumTexts)
