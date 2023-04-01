/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.PostgresqlCustomers
import org.ufoss.kotysa.test.postgresqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectOrderByRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectOrderByTest

class SpringJdbcSelectOrderByPostgresqlTest : AbstractSpringJdbcPostgresqlTest<OrderByRepositoryPostgresqlSelect>(),
    SelectOrderByTest<PostgresqlCustomers, OrderByRepositoryPostgresqlSelect, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        OrderByRepositoryPostgresqlSelect(jdbcOperations)
}

class OrderByRepositoryPostgresqlSelect(client: JdbcOperations) :
    SelectOrderByRepository<PostgresqlCustomers>(client.sqlClient(postgresqlTables), PostgresqlCustomers)
