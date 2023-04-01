/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.postgresql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.PostgresqlJdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.PostgresqlCustomers
import org.ufoss.kotysa.test.repositories.blocking.SelectOrderByRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectOrderByTest

class JdbcSelectOrderByPostgresqlTest : AbstractJdbcPostgresqlTest<OrderByRepositoryPostgresqlSelect>(),
    SelectOrderByTest<PostgresqlCustomers, OrderByRepositoryPostgresqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: PostgresqlJdbcSqlClient) =
        OrderByRepositoryPostgresqlSelect(sqlClient)
}

class OrderByRepositoryPostgresqlSelect(sqlClient: JdbcSqlClient) :
    SelectOrderByRepository<PostgresqlCustomers>(sqlClient, PostgresqlCustomers)
