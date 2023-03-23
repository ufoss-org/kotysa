/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.PostgresqlLocalDateTimeAsTimestamps
import org.ufoss.kotysa.test.postgresqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateTimeAsTimestampRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateTimeAsTimestampTest

class SpringJdbcSelectLocalDateTimeAsTimestampPostgresqlTest :
    AbstractSpringJdbcPostgresqlTest<LocalDateTimeAsTimestampRepositoryPostgresqlSelect>(),
    SelectLocalDateTimeAsTimestampTest<PostgresqlLocalDateTimeAsTimestamps,
            LocalDateTimeAsTimestampRepositoryPostgresqlSelect, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        LocalDateTimeAsTimestampRepositoryPostgresqlSelect(jdbcOperations)
}

class LocalDateTimeAsTimestampRepositoryPostgresqlSelect(client: JdbcOperations) :
    SelectLocalDateTimeAsTimestampRepository<PostgresqlLocalDateTimeAsTimestamps>(
        client.sqlClient(postgresqlTables),
        PostgresqlLocalDateTimeAsTimestamps
    )
