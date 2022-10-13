/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.PostgresqlCustomers
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.postgresqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectGroupByRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectGroupByTest

class SpringJdbcSelectGroupByPostgresqlTest : AbstractSpringJdbcPostgresqlTest<GroupByRepositoryPostgresqlSelect>(),
    SelectGroupByTest<PostgresqlCustomers, GroupByRepositoryPostgresqlSelect, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<GroupByRepositoryPostgresqlSelect>(resource)
    }

    override val repository: GroupByRepositoryPostgresqlSelect by lazy {
        getContextRepository()
    }
}

class GroupByRepositoryPostgresqlSelect(client: JdbcOperations) :
    SelectGroupByRepository<PostgresqlCustomers>(client.sqlClient(postgresqlTables), PostgresqlCustomers)
