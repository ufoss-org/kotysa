/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.H2LocalDateTimes
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateTimeRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateTimeTest

class JdbcSelectLocalDateTimeH2Test : AbstractJdbcH2Test<LocalDateTimeRepositoryH2Select>(),
    SelectLocalDateTimeTest<H2LocalDateTimes, LocalDateTimeRepositoryH2Select, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = LocalDateTimeRepositoryH2Select(sqlClient)
}

class LocalDateTimeRepositoryH2Select(sqlClient: JdbcSqlClient) :
    SelectLocalDateTimeRepository<H2LocalDateTimes>(sqlClient, H2LocalDateTimes)
