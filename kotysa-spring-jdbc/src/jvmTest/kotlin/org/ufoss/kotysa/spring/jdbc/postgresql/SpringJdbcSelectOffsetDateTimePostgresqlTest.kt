/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.PostgresqlOffsetDateTimes
import org.ufoss.kotysa.test.postgresqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectOffsetDateTimeRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectOffsetDateTimeTest

class SpringJdbcSelectOffsetDateTimePostgresqlTest :
    AbstractSpringJdbcPostgresqlTest<OffsetDateTimeRepositoryPostgresqlSelect>(),
    SelectOffsetDateTimeTest<PostgresqlOffsetDateTimes, OffsetDateTimeRepositoryPostgresqlSelect,
            SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        OffsetDateTimeRepositoryPostgresqlSelect(jdbcOperations)
}

class OffsetDateTimeRepositoryPostgresqlSelect(client: JdbcOperations) :
    SelectOffsetDateTimeRepository<PostgresqlOffsetDateTimes>(
        client.sqlClient(postgresqlTables),
        PostgresqlOffsetDateTimes
    )
