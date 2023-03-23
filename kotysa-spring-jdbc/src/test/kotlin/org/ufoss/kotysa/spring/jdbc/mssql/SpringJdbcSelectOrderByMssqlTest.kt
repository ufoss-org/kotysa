/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mssql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MssqlCustomers
import org.ufoss.kotysa.test.mssqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectOrderByRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectOrderByTest

class SpringJdbcSelectOrderByMssqlTest : AbstractSpringJdbcMssqlTest<OrderByRepositoryMssqlSelect>(),
    SelectOrderByTest<MssqlCustomers, OrderByRepositoryMssqlSelect, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) = OrderByRepositoryMssqlSelect(jdbcOperations)
}

class OrderByRepositoryMssqlSelect(client: JdbcOperations) :
    SelectOrderByRepository<MssqlCustomers>(client.sqlClient(mssqlTables), MssqlCustomers)
