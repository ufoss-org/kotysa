/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.H2OffsetDateTimes
import org.ufoss.kotysa.test.repositories.blocking.SelectOffsetDateTimeRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectOffsetDateTimeTest

class JdbcSelectOffsetDateTimeH2Test : AbstractJdbcH2Test<OffsetDateTimeRepositoryH2Select>(),
    SelectOffsetDateTimeTest<H2OffsetDateTimes, OffsetDateTimeRepositoryH2Select, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = OffsetDateTimeRepositoryH2Select(sqlClient)
}

class OffsetDateTimeRepositoryH2Select(sqlClient: JdbcSqlClient) :
    SelectOffsetDateTimeRepository<H2OffsetDateTimes>(sqlClient, H2OffsetDateTimes)
