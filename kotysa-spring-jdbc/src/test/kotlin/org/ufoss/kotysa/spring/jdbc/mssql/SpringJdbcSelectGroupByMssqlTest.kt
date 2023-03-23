/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mssql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MssqlCustomers
import org.ufoss.kotysa.test.mssqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectGroupByRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectGroupByTest

class SpringJdbcSelectGroupByMssqlTest : AbstractSpringJdbcMssqlTest<GroupByRepositoryMssqlSelect>(),
    SelectGroupByTest<MssqlCustomers, GroupByRepositoryMssqlSelect, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) = GroupByRepositoryMssqlSelect(jdbcOperations)
}

class GroupByRepositoryMssqlSelect(client: JdbcOperations) :
    SelectGroupByRepository<MssqlCustomers>(client.sqlClient(mssqlTables), MssqlCustomers)
