/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.H2LocalTimes
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalTimeRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalTimeTest

class JdbcSelectLocalTimeH2Test : AbstractJdbcH2Test<LocalTimeRepositoryH2Select>(),
    SelectLocalTimeTest<H2LocalTimes, LocalTimeRepositoryH2Select, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = LocalTimeRepositoryH2Select(sqlClient)
}

class LocalTimeRepositoryH2Select(sqlClient: JdbcSqlClient) :
    SelectLocalTimeRepository<H2LocalTimes>(sqlClient, H2LocalTimes)
