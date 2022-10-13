/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.postgresql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.PostgresqlKotlinxLocalDateTimeAsTimestamps
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateTimeAsTimestampRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateTimeAsTimestampTest

class JdbcSelectKotlinxLocalDateTimeAsTimestampPostgresqlTest :
    AbstractJdbcPostgresqlTest<KotlinxLocalDateTimeAsTimestampRepositoryPostgresqlSelect>(),
    SelectKotlinxLocalDateTimeAsTimestampTest<PostgresqlKotlinxLocalDateTimeAsTimestamps,
            KotlinxLocalDateTimeAsTimestampRepositoryPostgresqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) =
        KotlinxLocalDateTimeAsTimestampRepositoryPostgresqlSelect(sqlClient)
}

class KotlinxLocalDateTimeAsTimestampRepositoryPostgresqlSelect(sqlClient: JdbcSqlClient) :
    SelectKotlinxLocalDateTimeAsTimestampRepository<PostgresqlKotlinxLocalDateTimeAsTimestamps>(
        sqlClient,
        PostgresqlKotlinxLocalDateTimeAsTimestamps
    )
