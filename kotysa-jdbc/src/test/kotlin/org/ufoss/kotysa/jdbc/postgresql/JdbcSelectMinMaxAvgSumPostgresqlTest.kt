/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.postgresql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.PostgresqlCustomers
import org.ufoss.kotysa.test.repositories.blocking.SelectMinMaxAvgSumRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectMinMaxAvgSumTest

class JdbcSelectMinMaxAvgSumH2Test : AbstractJdbcPostgresqlTest<MinMaxAvgSumRepositoryPostgresqlSelect>(),
    SelectMinMaxAvgSumTest<PostgresqlCustomers, MinMaxAvgSumRepositoryPostgresqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = MinMaxAvgSumRepositoryPostgresqlSelect(sqlClient)
}

class MinMaxAvgSumRepositoryPostgresqlSelect(sqlClient: JdbcSqlClient) :
    SelectMinMaxAvgSumRepository<PostgresqlCustomers>(sqlClient, PostgresqlCustomers)
