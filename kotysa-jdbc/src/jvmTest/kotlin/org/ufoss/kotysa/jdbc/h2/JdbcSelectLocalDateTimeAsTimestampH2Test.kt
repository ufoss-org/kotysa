/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.H2LocalDateTimeAsTimestamps
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateTimeAsTimestampRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateTimeAsTimestampTest

class JdbcSelectLocalDateTimeAsTimestampH2Test : AbstractJdbcH2Test<LocalDateTimeAsTimestampRepositoryH2Select>(),
    SelectLocalDateTimeAsTimestampTest<H2LocalDateTimeAsTimestamps, LocalDateTimeAsTimestampRepositoryH2Select,
            JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = LocalDateTimeAsTimestampRepositoryH2Select(sqlClient)
}

class LocalDateTimeAsTimestampRepositoryH2Select(sqlClient: JdbcSqlClient) :
    SelectLocalDateTimeAsTimestampRepository<H2LocalDateTimeAsTimestamps>(sqlClient, H2LocalDateTimeAsTimestamps)
