/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.H2LocalDateTimeAsTimestamps
import org.ufoss.kotysa.test.h2Tables
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateTimeAsTimestampRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateTimeAsTimestampTest

class SpringJdbcSelectLocalDateTimeAsTimestampH2Test :
    AbstractSpringJdbcH2Test<LocalDateTimeAsTimestampRepositoryH2Select>(),
    SelectLocalDateTimeAsTimestampTest<H2LocalDateTimeAsTimestamps, LocalDateTimeAsTimestampRepositoryH2Select,
            SpringJdbcTransaction> {
    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        LocalDateTimeAsTimestampRepositoryH2Select(jdbcOperations)
}

class LocalDateTimeAsTimestampRepositoryH2Select(client: JdbcOperations) :
    SelectLocalDateTimeAsTimestampRepository<H2LocalDateTimeAsTimestamps>(
        client.sqlClient(h2Tables),
        H2LocalDateTimeAsTimestamps
    )
