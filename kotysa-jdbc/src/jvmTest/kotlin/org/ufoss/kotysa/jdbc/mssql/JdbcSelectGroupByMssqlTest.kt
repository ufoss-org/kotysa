/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mssql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MssqlCustomers
import org.ufoss.kotysa.test.repositories.blocking.SelectGroupByRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectGroupByTest

class JdbcSelectGroupByMssqlTest : AbstractJdbcMssqlTest<GroupByRepositoryMssqlSelect>(),
    SelectGroupByTest<MssqlCustomers, GroupByRepositoryMssqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = GroupByRepositoryMssqlSelect(sqlClient)
}

class GroupByRepositoryMssqlSelect(sqlClient: JdbcSqlClient) :
    SelectGroupByRepository<MssqlCustomers>(sqlClient, MssqlCustomers)
