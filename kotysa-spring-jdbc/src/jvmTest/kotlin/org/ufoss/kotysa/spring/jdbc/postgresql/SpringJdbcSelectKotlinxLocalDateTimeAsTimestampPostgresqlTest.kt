/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.PostgresqlKotlinxLocalDateTimeAsTimestamps
import org.ufoss.kotysa.test.postgresqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateTimeAsTimestampRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateTimeAsTimestampTest

class SpringJdbcSelectKotlinxLocalDateTimeAsTimestampPostgresqlTest :
    AbstractSpringJdbcPostgresqlTest<KotlinxLocalDateTimeAsTimestampRepositoryPostgresqlSelect>(),
    SelectKotlinxLocalDateTimeAsTimestampTest<PostgresqlKotlinxLocalDateTimeAsTimestamps,
            KotlinxLocalDateTimeAsTimestampRepositoryPostgresqlSelect, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        KotlinxLocalDateTimeAsTimestampRepositoryPostgresqlSelect(jdbcOperations)
}

class KotlinxLocalDateTimeAsTimestampRepositoryPostgresqlSelect(client: JdbcOperations) :
    SelectKotlinxLocalDateTimeAsTimestampRepository<PostgresqlKotlinxLocalDateTimeAsTimestamps>(
        client.sqlClient(postgresqlTables),
        PostgresqlKotlinxLocalDateTimeAsTimestamps
    )
