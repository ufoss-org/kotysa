/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.PostgresqlOffsetDateTimes
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.postgresqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectOffsetDateTimeRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectOffsetDateTimeTest

class SpringJdbcSelectOffsetDateTimePostgresqlTest :
    AbstractSpringJdbcPostgresqlTest<OffsetDateTimeRepositoryPostgresqlSelect>(),
    SelectOffsetDateTimeTest<PostgresqlOffsetDateTimes, OffsetDateTimeRepositoryPostgresqlSelect,
            SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<OffsetDateTimeRepositoryPostgresqlSelect>(resource)
    }

    override val repository: OffsetDateTimeRepositoryPostgresqlSelect by lazy {
        getContextRepository()
    }
}

class OffsetDateTimeRepositoryPostgresqlSelect(client: JdbcOperations) :
    SelectOffsetDateTimeRepository<PostgresqlOffsetDateTimes>(
        client.sqlClient(postgresqlTables),
        PostgresqlOffsetDateTimes
    )
