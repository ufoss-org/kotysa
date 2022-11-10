/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.postgresql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.PostgresqlJdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.PostgresqlCustomers
import org.ufoss.kotysa.test.repositories.blocking.SelectGroupByRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectGroupByTest

class JdbcSelectGroupByPostgresqlTest : AbstractJdbcPostgresqlTest<GroupByRepositoryPostgresqlSelect>(),
    SelectGroupByTest<PostgresqlCustomers, GroupByRepositoryPostgresqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: PostgresqlJdbcSqlClient) =
        GroupByRepositoryPostgresqlSelect(sqlClient)
}

class GroupByRepositoryPostgresqlSelect(sqlClient: JdbcSqlClient) :
    SelectGroupByRepository<PostgresqlCustomers>(sqlClient, PostgresqlCustomers)
