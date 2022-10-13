/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.PostgresqlLocalTimes
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.postgresqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalTimeRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalTimeTest

class SpringJdbcSelectLocalTimePostgresqlTest : AbstractSpringJdbcPostgresqlTest<LocalTimeRepositoryPostgresqlSelect>(),
    SelectLocalTimeTest<PostgresqlLocalTimes, LocalTimeRepositoryPostgresqlSelect, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<LocalTimeRepositoryPostgresqlSelect>(resource)
    }

    override val repository: LocalTimeRepositoryPostgresqlSelect by lazy {
        getContextRepository()
    }
}

class LocalTimeRepositoryPostgresqlSelect(client: JdbcOperations) :
    SelectLocalTimeRepository<PostgresqlLocalTimes>(client.sqlClient(postgresqlTables), PostgresqlLocalTimes)
