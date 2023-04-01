/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.postgresql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.PostgresqlJdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.PostgresqlLocalDateTimeAsTimestamps
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateTimeAsTimestampRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateTimeAsTimestampTest

class JdbcSelectLocalDateTimeAsTimestampPostgresqlTest :
    AbstractJdbcPostgresqlTest<LocalDateTimeAsTimestampRepositoryPostgresqlSelect>(),
    SelectLocalDateTimeAsTimestampTest<PostgresqlLocalDateTimeAsTimestamps,
            LocalDateTimeAsTimestampRepositoryPostgresqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: PostgresqlJdbcSqlClient) =
        LocalDateTimeAsTimestampRepositoryPostgresqlSelect(sqlClient)
}

class LocalDateTimeAsTimestampRepositoryPostgresqlSelect(sqlClient: JdbcSqlClient) :
    SelectLocalDateTimeAsTimestampRepository<PostgresqlLocalDateTimeAsTimestamps>(
        sqlClient,
        PostgresqlLocalDateTimeAsTimestamps
    )
