/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mssql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MssqlCustomers
import org.ufoss.kotysa.test.repositories.blocking.SelectOrderByRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectOrderByTest

class JdbcSelectOrderByMssqlTest : AbstractJdbcMssqlTest<OrderByRepositoryMssqlSelect>(),
    SelectOrderByTest<MssqlCustomers, OrderByRepositoryMssqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = OrderByRepositoryMssqlSelect(sqlClient)
}

class OrderByRepositoryMssqlSelect(sqlClient: JdbcSqlClient) :
    SelectOrderByRepository<MssqlCustomers>(sqlClient, MssqlCustomers)
